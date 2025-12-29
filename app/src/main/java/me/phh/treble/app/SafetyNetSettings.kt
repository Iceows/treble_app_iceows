package me.phh.treble.app

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference

object SafetyNetSettings : Settings {
    val securePhone = "key_misc_securizephone"
    val secureAdb = "key_misc_secure_adb"
    val removesu = "key_misc_removesu"
    val safetyNetSpoof = "key_misc_safetyspoof"
    val safetyNetSpoofModel = "key_misc_safetyspoof_model"
    val safetyNetSpoofDevice = "key_misc_safetyspoof_device"
    val safetyNetSpoofProduct= "key_misc_safetyspoof_product"
    val safetyNetSpoofFingerprint= "key_misc_safetyspoof_fingerprint"

    override fun enabled() = true
}


class SafetyNetSettingsFragment : SettingsFragment() {
    override val preferencesResId = R.xml.pref_safetynet

}