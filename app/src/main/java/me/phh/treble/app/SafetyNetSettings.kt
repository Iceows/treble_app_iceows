package me.phh.treble.app

import android.app.AlertDialog
import android.os.Bundle
import android.os.SystemProperties
import android.util.Log
import androidx.preference.Preference

object SafetyNetSettings : Settings {

    val secureAdb = "key_misc_secure_adb"
    val securePhonePhh = "key_misc_securize_phh"
    val securePhoneIceows = "key_misc_securize"
    val safetyNetSpoof = "key_misc_safetyspoof"
    val safetyNetSpoofModel = "key_misc_safetyspoof_model"
    val safetyNetSpoofDevice = "key_misc_safetyspoof_device"
    val safetyNetSpoofProduct= "key_misc_safetyspoof_product"
    val safetyNetSpoofFingerprint= "key_misc_safetyspoof_fingerprint"

    override fun enabled() = true
}


class SafetyNetSettingsFragment : SettingsFragment() {
    override val preferencesResId = R.xml.pref_safetynet

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)


        val securePhoneIceowsPref = findPreference<Preference>(SafetyNetSettings.securePhoneIceows)
        securePhoneIceowsPref!!.setOnPreferenceClickListener {

            val builder = AlertDialog.Builder( this.getActivity() )
            builder.setTitle("Safetynet Securize")
            builder.setMessage(getString(R.string.continue_question))
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Log.d("PHH", "Dynamic Securize")
                SystemProperties.set("persist.sys.phh.securize",  "true")
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
            }

            builder.show()
            return@setOnPreferenceClickListener true
        }


        val securePHHPref = findPreference<Preference>(SafetyNetSettings.securePhonePhh)
        securePHHPref!!.setOnPreferenceClickListener {

            val builder = AlertDialog.Builder( this.getActivity() )
            builder.setTitle("PHH Securize")
            builder.setMessage(getString(R.string.continue_question))
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Log.d("PHH", "PHH Securize")
                var cmds = listOf(
                    arrayOf("/sbin/su", "-c", "/system/bin/phh-securize.sh"),
                    arrayOf("/system/xbin/su", "-c", "/system/bin/phh-securize.sh"),
                    arrayOf("/system/xbin/phh-su", "-c", "/system/bin/phh-securize.sh"),
                    arrayOf("/sbin/su", "0", "/system/bin/phh-securize.sh"),
                    arrayOf("/system/xbin/su", "0", "/system/bin/phh-securize.sh"),
                    arrayOf("/system/xbin/phh-su", "0", "/system/bin/phh-securize.sh")
                )
                for (cmd in cmds) {
                    try {
                        Runtime.getRuntime().exec(cmd).waitFor()
                        break
                    } catch (t: Throwable) {
                        Log.d(
                            "PHH",
                            "Failed to exec \"" + cmd.joinToString(separator = " ") + "\", skipping"
                        )
                    }
                }
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
            }

            builder.show()
            return@setOnPreferenceClickListener true

        }

    }
}