/*
 * Copyright (C) 2021 crDroid Android Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

#include <libinit_dalvik_heap.h>
#include <libinit_variant.h>

#include "vendor_init.h"

static const variant_info_t sweet_global_info = {
    .hwc_value = "GLOBAL",
    .sku_value = "",

    .brand = "Redmi",
    .device = "sweet",
    .marketname = "Redmi Note 10 Pro",
    .model = "Redmi Note 10 Pro",
    .build_fingerprint = "Redmi/sweet_global/sweet:11/RKQ1.200826.002/V12.5.4.0.RKFMIXM:user/release-keys",

    .nfc = true,
};

static const variant_info_t sweet_eea_info = {
    .hwc_value = "EEA",
    .sku_value = "",

    .brand = "Redmi",
    .device = "sweet",
    .marketname = "Redmi Note 10 Pro",
    .model = "Redmi Note 10 Pro",
    .build_fingerprint = "Redmi/sweet_eea/sweet:11/RKQ1.200826.002/V12.5.4.0.RKFEUXM:user/release-keys",

    .nfc = true,
};

static const variant_info_t sweetin_info = {
    .hwc_value = "INDIA",
    .sku_value = "std",

    .brand = "Redmi",
    .device = "sweetin",
    .marketname = "Redmi Note 10 Pro",
    .model = "Redmi Note 10 Pro",
    .build_fingerprint = "Redmi/sweetin/sweetin:11/RKQ1.200826.002/V12.5.8.0.RKFINXM:user/release-keys",

    .nfc = false,
};

static const variant_info_t sweetinpro_info = {
    .hwc_value = "INDIA",
    .sku_value = "pro",

    .brand = "Redmi",
    .device = "sweetinpro",
    .marketname = "Redmi Note 10 Pro Max",
    .model = "Redmi Note 10 Pro Max",
    .build_fingerprint = "Redmi/sweetinpro/sweetin:11/RKQ1.200826.002/V12.5.8.0.RKFINXM:user/release-keys",

    .nfc = false,
};

static const std::vector<variant_info_t> variants = {
    sweet_global_info,
    sweet_eea_info,
    sweetin_info,
    sweetinpro_info,
};

void vendor_load_properties() {
    set_dalvik_heap();
    search_variant(variants);
}
