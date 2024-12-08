package me.phh.treble.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.hardware.display.DisplayManager
import android.os.SystemProperties
import android.preference.PreferenceManager
import android.util.Log
import java.lang.ref.WeakReference


@SuppressLint("StaticFieldLeak")
class SafetyNet: EntryStartup {
    var ctxt: Context? = null

    fun safeSetprop(key: String, value: String?) {
        try {
            Log.d("PHH", "Setting property $key to $value")
            SystemProperties.set(key, value)
        } catch (e: Exception) {
            Log.d("PHH", "Failed setting prop $key", e)
        }
    }

    val spListener = SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
        when(key) {

            SafetyNetSettings.securize -> {
                val value = sp.getBoolean(key, false)
                SystemProperties.set("persist.sys.phh.securize", if (value) "true" else "false")
                Log.d("PHH", "Set Securize to $value")

            }
            SafetyNetSettings.safetyNetSpoof -> {
                val value = sp.getBoolean(key, false)
                SystemProperties.set("persist.sys.phh.safetyspoof", if (value) "true" else "false")
                Log.d("PHH", "Set Safetyspoof to $value")
            }
            SafetyNetSettings.safetyNetSpoofModel-> {
                val value = sp.getString(key, "1")
                android.util.Log.d("PHH", "Setting spoof model to $value")

                when (value) {
                    //Google Pixel XL (7.1 & 7.1.1 & 7.1.2 & 8.0.0 & 8.1.0 & 9 & 10):
                    // Google:Pixel XL=google/marlin/marlin:7.1/NDE63V/3389651:user/release-keys;
                    // google/marlin/marlin:7.1.1/NOF27D/3757586:user/release-keys;
                    // google/marlin/marlin:7.1.2/NJH47F/4146041:user/release-keys;
                    // google/marlin/marlin:8.0.0/OPR3.170623.013/4397526:user/release-keys;
                    // google/marlin/marlin:8.1.0/OPM4.171019.021.P1/4820305:user/release-keys__2018-07-05;
                    // google/marlin/marlin:9/PQ3A.190801.002/5670241:user/release-keys__2019-08-01;
                    // google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys__2019-10-06
                    "marlin" -> {
                        SystemProperties.set("persist.sys.phh.safetyspoof.model", "Pixel XL")
                        SystemProperties.set("persist.sys.phh.safetyspoof.product", "marlin")
                        SystemProperties.set("persist.sys.phh.safetyspoof.device", "marlin")
                        SystemProperties.set("persist.sys.phh.safetyspoof.fingerprint", "google/marlin/marlin:7.1.2/NJH47F/4146041:user/release-keys")
                    }
                    // Google Pixel (7.1 & 7.1.1 & 7.1.2 & 8.0.0 & 8.1.0 & 9 & 10):
                    // Google:Pixel=google/sailfish/sailfish:7.1/NDE63V/3389651:user/release-keys;
                    // google/sailfish/sailfish:7.1.1/NOF27D/3757586:user/release-keys;
                    // google/sailfish/sailfish:7.1.2/NJH47F/4146041:user/release-keys;
                    // google/sailfish/sailfish:8.0.0/OPR3.170623.013/4397526:user/release-keys;
                    // google/sailfish/sailfish:8.1.0/OPM4.171019.021.P1/4820305:user/release-keys__2018-07-05;
                    // google/sailfish/sailfish:9/PQ3A.190801.002/5670241:user/release-keys__2019-08-01;google/sailfish/sailfish:10/QP1A.191005.007.A3/5972272:user/release-keys__2019-10-06
                    "sailfish" -> {
                        SystemProperties.set("persist.sys.phh.safetyspoof.model", "Google Pixel")
                        SystemProperties.set("persist.sys.phh.safetyspoof.product", "sailfish")
                        SystemProperties.set("persist.sys.phh.safetyspoof.device", "sailfish")
                        SystemProperties.set("persist.sys.phh.safetyspoof.fingerprint", "google/sailfish/sailfish:7.1/NDE63V/3389651:user/release-keys")
                    }
                    //Huawei P20 Lite (9):
                    // Huawei:ANE-LX1=HUAWEI/ANE-LX1/HWANE:9/HUAWEIANE-L01/9.1.0.368C432:user/release-keys__2020-08-01
                    //Huawei P20 Lite Dual SIM (8.0.0 & 9):
                    // Huawei:ANE-LX1=HUAWEI/ANE-LX1/HWANE:8.0.0/HUAWEIANE-LX1/180(C432):user/release-keys__2019-04-05
                    // HUAWEI/ANE-LX1/HWANE:9/HUAWEIANE-L21/132C432R1:user/release-keys__2019-05-05
                    "ANE-LX1" -> {
                        SystemProperties.set("persist.sys.phh.safetyspoof.model", "ANE-LX1")
                        SystemProperties.set("persist.sys.phh.safetyspoof.product", "HWANE")
                        SystemProperties.set("persist.sys.phh.safetyspoof.device", "ANE-LX1")
                        SystemProperties.set("persist.sys.phh.safetyspoof.fingerprint", "HUAWEI/ANE-LX1/HWANE:9/HUAWEIANE-L01/9.1.0.368C432:user/release-keys__2020-08-01")
                    }
                    "PRA-LX1" -> {

                    }
                    "FIG-LX1" -> {

                    }
                    "POT-LX1" -> {

                    }
                    "POT-LX1A" -> {

                    }
                    //Huawei Honor 9 STF-L09 (8.0.0 & 9)
                    // Huawei:STF-L09=HONOR/STF-L09/HWSTF:8.0.0/HUAWEISTF-L09/364(C432):user/release-keys
                    // HONOR/STF-L09/HWSTF:9/HUAWEISTF-L09/157C432R1:user/release-keys__2019-02-01
                    "STF-L09" -> {
                        SystemProperties.set("persist.sys.phh.safetyspoof.model", "STF-L09")
                        SystemProperties.set("persist.sys.phh.safetyspoof.product", "STF-L09")
                        SystemProperties.set("persist.sys.phh.safetyspoof.device", "HWSTF")
                        SystemProperties.set("persist.sys.phh.safetyspoof.fingerprint", "HONOR/STF-L09/HWSTF:9/HUAWEISTF-L09/9.1.0.220C432:user/release-keys")
                    }

                }
            }

        }
    }


    override fun startup(ctxt: Context) {
        //if (!SafetyNetSettings.enabled()) return

        val sp = PreferenceManager.getDefaultSharedPreferences(ctxt)
        sp.registerOnSharedPreferenceChangeListener(spListener)

        this.ctxt = ctxt.applicationContext

        spListener.onSharedPreferenceChanged(sp, SafetyNetSettings.safetyNetSpoof)
        spListener.onSharedPreferenceChanged(sp, SafetyNetSettings.safetyNetSpoofModel)
        spListener.onSharedPreferenceChanged(sp, SafetyNetSettings.securize)
    }


    companion object: EntryStartup {
        private var self: SafetyNet? = null
        override fun startup(ctxt: Context) {
            if (!HuaweiSettings.enabled()) return
            self = SafetyNet()
            self!!.startup(ctxt)
        }
    }
}