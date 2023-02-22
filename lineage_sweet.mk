#
# Copyright (C) 2021-2022 crDroid Android Project
#
# SPDX-License-Identifier: Apache-2.0
#

$(call inherit-product, device/xiaomi/sweet/device.mk)

# Inherit some common Lineage stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Device identifier. This must come after all inclusions.
PRODUCT_NAME := lineage_sweet
PRODUCT_DEVICE := sweet
PRODUCT_BRAND := Redmi
PRODUCT_MODEL := Redmi Note 10 Pro
PRODUCT_MANUFACTURER := Xiaomi

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="sweet-user 12 RKQ1.210614.002 V13.0.16.0.SKFMIXM release-keys"

BUILD_FINGERPRINT := Redmi/sweet_global/sweet:12/RKQ1.210614.002/V13.0.16.0.SKFMIXM:user/release-keys
