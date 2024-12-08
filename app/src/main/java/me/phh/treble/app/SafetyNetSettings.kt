package me.phh.treble.app

import android.os.SystemProperties

object SafetyNetSettings : Settings {
    val securize = "key_misc_securize"
    val safetyNetSpoof = "key_misc_safetyspoof"
    val safetyNetSpoofModel = "key_misc_safetyspoof_model"
    val safetyNetSpoofDevice = "key_misc_safetyspoof_device"
    val safetyNetSpoofProduct= "key_misc_safetyspoof_product"
    val safetyNetSpoofFingerprint= "key_misc_safetyspoof_fingerprint"

    override fun enabled(): Boolean =
        Tools.vendorFpLow.contains("huawei") ||
                Tools.vendorFpLow.contains("honor") ||
                SystemProperties.getBoolean("persist.sys.overlay.huawei", false)
}

class SafetyNetSettingsFragment : SettingsFragment() {
    override val preferencesResId = R.xml.pref_safetynet
}