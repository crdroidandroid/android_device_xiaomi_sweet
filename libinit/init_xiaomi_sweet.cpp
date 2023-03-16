/*
 * Copyright (C) 2021-2022 crDroid Android Project
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
    .mod_device = "sweet_global",
    .marketname = "Redmi Note 10 Pro",
    .model = "M2101K6G",
    .build_fingerprint = "Redmi/sweet_global/sweet:13/RKQ1.210614.002/V14.0.1.0.TKFMIXM:user/release-keys",

    .nfc = true,
};

static const variant_info_t sweet_eea_info = {
    .hwc_value = "EEA",
    .sku_value = "",

    .brand = "Redmi",
    .device = "sweet",
    .mod_device = "sweet_eea_global",
    .marketname = "Redmi Note 10 Pro",
    .model = "M2101K6G",
    .build_fingerprint = "Redmi/sweet_eea/sweet:13/RKQ1.210614.002/V14.0.2.0.TKFEUXM:user/release-keys",

    .nfc = true,
};

static const variant_info_t sweetin_info = {
    .hwc_value = "INDIA",
    .sku_value = "std",

    .brand = "Redmi",
    .device = "sweetin",
    .mod_device = "sweetin_in_global",
    .marketname = "Redmi Note 10 Pro",
    .model = "M2101K6P",
    .build_fingerprint = "Redmi/sweetin/sweetin:12/RKQ1.210614.002/V13.0.9.0.SKFINXM:user/release-keys",

    .nfc = false,
};

static const variant_info_t sweetinpro_info = {
    .hwc_value = "INDIA",
    .sku_value = "pro",

    .brand = "Redmi",
    .device = "sweetin",
    .mod_device = "sweetin_in_global",
    .marketname = "Redmi Note 10 Pro Max",
    .model = "M2101K6I",
    .build_fingerprint = "Redmi/sweetinpro/sweetin:12/RKQ1.210614.002/V13.0.9.0.SKFINXM:user/release-keys",

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
