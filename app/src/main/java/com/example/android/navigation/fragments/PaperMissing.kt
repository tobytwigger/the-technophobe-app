package com.example.android.navigation.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkSpecifier
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentsFragmentPaperMissingBinding
import java.util.logging.Logger

class PaperMissing : Fragment() {

    lateinit var wifiManager: WifiManager

    private lateinit var ssid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val binding = DataBindingUtil.inflate<FragmentsFragmentPaperMissingBinding>(inflater, R.layout.fragments_fragment_paper_missing, container, false)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        ssid = sharedPreferences.getString("ssid", "")


        binding.paperMissing.setOnClickListener { v: View ->
            var isConnected: Boolean = isConnected()

            if(isConnected && getCurrentNetwork()?.ssid?.drop(1)?.dropLast(1) == ssid) {
                Toast.makeText(context, "Already connected to the internet.", Toast.LENGTH_LONG).show()
            } else {
                if(isConnected) {
                    turnOffConnection()
                }

                var netId: Int = findNetId()
                if(netId == -1) {
                    Toast.makeText(context, "Could not find internet. Restart it!", Toast.LENGTH_LONG).show()
                }
                else if(connectToNetwork(netId)) {
                    Toast.makeText(context, "Connected to the internet, is your newspaper appearing now?", Toast.LENGTH_LONG).show();
                }
            }

        }
        return binding.root
    }

    private fun isConnected(): Boolean {
        val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return (networkInfo?.isConnected ?: false)
    }

    private fun getCurrentNetwork(): WifiInfo? {
        return wifiManager.connectionInfo
    }

    private fun turnOffConnection() {
        wifiManager.disconnect()
    }

    private fun findNetId(): Int  {
        // Get configured networks
        var networks = wifiManager.configuredNetworks
        return (networks?.find { wifi: WifiConfiguration -> wifi.SSID.drop(1).dropLast(1) == ssid }?.networkId ?: -1)
    }

    private fun connectToNetwork(netId: Int): Boolean {
        return wifiManager.enableNetwork(netId, true)
    }
//    object WifiUtils {
//
//        lateinit var wifiManager: WifiManager
//
//        fun connect() {
//            // Get the scan results
//            wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//            var scanResults = getScanResults(context)
//
//        }
//
//        fun getScanResults(context: Context) {
//            val wifiScanReceiver = object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
//                    if (success) {
//                        scanSuccess()
//                    } else {
//                        scanFailure()
//                    }
//                }
//            }
//
//            val intentFilter = IntentFilter()
//            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//            context.registerReceiver(wifiScanReceiver, intentFilter)
//
//            val success = wifiManager.startScan()
//            if (!success) {
//                // scan failure handling
//                scanFailure()
//            }
//        }
//
//
//
//
//        private fun scanSuccess() {
//            val results = wifiManager.scanResults
//        }
//
//        private fun scanFailure() {
//            // handle failure: new scan did NOT succeed
//            // consider using old scan results: these are the OLD results!
//            val results = wifiManager.scanResults
//        }
//
//    }
//    private fun isConnected()

}