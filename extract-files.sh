#!/bin/bash
#
# SPDX-FileCopyrightText: 2016 The CyanogenMod Project
# SPDX-FileCopyrightText: 2017-2024 The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

function blob_fixup() {
    case "${1}" in
        vendor/etc/init/init.batterysecret.rc)
            [ "$2" = "" ] && return 0
            sed -i "/seclabel u:r:batterysecret:s0/d" "${2}"
            ;;
        vendor/etc/init/init.mi_thermald.rc)
            [ "$2" = "" ] && return 0
            sed -i "/seclabel u:r:mi_thermald:s0/d" "${2}"
            ;;
        vendor/lib64/camera/components/com.qti.node.watermark.so)
            [ "$2" = "" ] && return 0
            grep -q "libpiex_shim.so" "${2}" || "${PATCHELF}" --add-needed "libpiex_shim.so" "${2}"
            ;;
        vendor/lib64/hw/camera.qcom.so | vendor/lib64/libFaceDetectpp-0.5.2.so | vendor/lib64/libfacedet.so)
            [ "$2" = "" ] && return 0
            sed -i "s|libmegface.so|libfacedet.so|g" "${2}"
            sed -i "s|libMegviiFacepp-0.5.2.so|libFaceDetectpp-0.5.2.so|g" "${2}"
            sed -i "s|megviifacepp_0_5_2_model|facedetectpp_0_5_2_model|g" "${2}"
            ;;
        vendor/lib64/libdlbdsservice.so | vendor/lib/libstagefright_soft_ac4dec.so | vendor/lib/libstagefright_soft_ddpdec.so)
            [ "$2" = "" ] && return 0
            "${PATCHELF}" --replace-needed "libstagefright_foundation.so" "libstagefright_foundation-v33.so" "${2}"
            ;;
        *)
            return 1
            ;;
    esac

    return 0
}

function blob_fixup_dry() {
    blob_fixup "$1" ""
}

# If we're being sourced by the common script that we called,
# stop right here. No need to go down the rabbit hole.
if [ "${BASH_SOURCE[0]}" != "${0}" ]; then
    return
fi

set -e

export DEVICE=sweet
export DEVICE_COMMON=sm6150-common
export VENDOR=xiaomi
export VENDOR_COMMON=${VENDOR}

"./../../${VENDOR_COMMON}/${DEVICE_COMMON}/extract-files.sh" "$@"
