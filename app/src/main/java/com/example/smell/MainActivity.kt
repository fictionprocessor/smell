package com.example.smell

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smell.ui.theme.SmellTheme
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

val TAG: String = "mfmf"

class MainActivity : ComponentActivity() {

    val context: Context = this

    private fun getLocalUserName(): String {
        // should return the name the user asks for
        val randomInteger = (0..99).shuffled().first().toString()
        return "anon$randomInteger"
    }

    var SERVICE_ID: String = "smell"

    private val STRATEGY = Strategy.P2P_CLUSTER

    var blahLoad = byteArrayOf(0xa, 0xb, 0xc, 0xd)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmellTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ComposeNavigation()
                }
            }
        }


        checkForPermission()
        startAdvertising()
        startDiscovery()
    }


    // =================================================================================
    // NEARBY CONNECTIONS
    // =================================================================================
    // ---------------------------------------------------------------------------------
    // Advertising and discovery
    // ---------------------------------------------------------------------------------
    // Advertisers begin by invoking startAdvertising(), passing in a
    // ConnectionLifecycleCallback which will be notified whenever a
    // Discoverer wants to connect via the onConnectionInitiated() callback.
    // https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionsClient#public-abstract-taskvoid-startadvertising-byte[]-endpointinfo,-string-serviceid,-connectionlifecyclecallback-connectionlifecyclecallback,-advertisingoptions-options
    // public abstract Task<Void> startAdvertising (byte[] endpointInfo, String serviceId, ConnectionLifecycleCallback connectionLifecycleCallback, AdvertisingOptions options)
    // endpointInfo 	Identifing information about this endpoint (eg. name, device type), to appear on the remote device. Defined by client/application.
    // serviceId 	An identifier to advertise your app to other endpoints. This can be an arbitrary string, so long as it uniquely identifies your service. A good default is to use your app's package name.
    // connectionLifecycleCallback 	A callback notified when remote endpoints request a connection to this endpoint.
    // options 	The options for advertising.

    private fun startAdvertising() {
        val advertisingOptions: AdvertisingOptions =
            AdvertisingOptions.Builder().setStrategy(STRATEGY).build()
        Nearby.getConnectionsClient(context)
            .startAdvertising(
                getLocalUserName(), SERVICE_ID, connectionLifecycleCallback, advertisingOptions
            )
            .addOnSuccessListener { unused: Void? -> }
            .addOnFailureListener { e: Exception? -> }

        Log.d(TAG, "started advertising...")
    }


    //
    // Discoverers begin by invoking startDiscovery(), passing in an
    // EndpointDiscoveryCallback which will be notified whenever a nearby Advertiser
    // is found via the onEndpointFound() callback.

    private fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(STRATEGY).build()
        Nearby.getConnectionsClient(context)
            .startDiscovery(SERVICE_ID, endpointDiscoveryCallback, discoveryOptions)
            .addOnSuccessListener { unused: Void? -> }
            .addOnFailureListener { e: java.lang.Exception? -> }

        Log.d(TAG, "started discovery...")


    }


    // ---------------------------------------------------------------------------------
    // Establishing connections
    // ---------------------------------------------------------------------------------


    // When a Discoverer wishes to connect to a nearby Advertiser, the Discoverer
    // invokes requestConnection(), passing in a ConnectionLifecycleCallback of its own.

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                // An endpoint was found. We request a connection to it.
                Nearby.getConnectionsClient(context)
                    .requestConnection(getLocalUserName(), endpointId, connectionLifecycleCallback)
                    .addOnSuccessListener { unused: Void? -> }
                    .addOnFailureListener { e: java.lang.Exception? -> }

                Log.d(TAG, "\nfound... $endpointId")
            }

            override fun onEndpointLost(endpointId: String) {
                // A previously discovered endpoint has gone away.
            }
        }

    //
    // Both sides are then notified of the connection initiation process via the
    // ConnectionLifecycleCallback.onConnectionInitiated() callback, and both must now
    // choose whether to accept or reject the connection via a call to
    // acceptConnection() or rejectConnection(), respectively.

    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                // Automatically accept the connection on both sides.
                Nearby.getConnectionsClient(context)
                    .acceptConnection(endpointId, ReceiveBytesPayloadListener)
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {

                        val toEndPoint: String = endpointId

                        Log.d(TAG, "\nsending data to...$toEndPoint")

                        sendIt(toEndPoint)
                    }
                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {}
                    ConnectionsStatusCodes.STATUS_ERROR -> {}
                    else -> {}
                }
            }

            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
                Log.d(TAG, "\nsuddenly disconnected from... $endpointId")
            }
        }


    //
    // At this point, apps can optionally prompt the user to accept the connection.
    // See Authenticate a connection to learn more.
    //
    // Once both sides have responded, each will be notified of the result via the
    // ConnectionLifecycleCallback.onConnectionResult() callback. If both sides
    // accepted the connection, then the ConnectionResolution provided in the callback
    // will be successful, the connection is considered established, and the transfer of
    // Payloads can then begin.


    // ---------------------------------------------------------------------------------
    // Exchanging data
    // ---------------------------------------------------------------------------------
    // After a connection is established, further API usage is symmetrical, so there’s no
    // longer a distinction between Advertiser and Discoverer.
    //
    // Both sides can now exchange data as Payload objects. There are 3 types of supported Payloads:
    //
    //    BYTES Byte arrays limited to 32k; these are good for sending things such as metadata or control messages.
    //    FILE Files of any size; these are transferred from the app to the network interface with minimal copying across process boundaries.
    //    STREAM A stream of data that is generated on the fly, as in the case of recorded audio/video, with no final size known beforehand.
    //
    // Senders use the sendPayload() method to send a Payload. This method can be invoked
    // multiple times, but since we guarantee in-order delivery, the second Payload onwards
    // will be queued for sending until the first Payload is done.

    // code from google connections tutorial
    //fun sendIt(toEndpointId: String) {
    //    val bytesPayload = Payload.fromBytes(byteArrayOf(0xa, 0xb, 0xc, 0xd))
    //    Nearby.getConnectionsClient(context).sendPayload(toEndpointId, bytesPayload)
    //}

    fun sendIt(toEndpointId: String){
        var bytesPayload = Payload.fromBytes(byteArrayOf(0xa, 0xb, 0xc, 0xd))
        // mf
        //if (masterBlah.isNotEmpty()){
         //   val json: ByteArray = Json.encodeToString(masterBlah).toByteArray()
        //    bytesPayload = Payload.fromBytes(json)
        // }
        // end mf
        Nearby.getConnectionsClient(context).sendPayload(toEndpointId, bytesPayload)
    }

    //
    // Receivers can expect the PayloadCallback.onPayloadReceived() callback to be invoked
    // when a new incoming Payload is received.

    //   private final PayloadCallback payloadCallback =
    //          new PayloadCallback() {
    //            @Override
    //            public void onPayloadReceived(String endpointId, Payload payload) {
    //              opponentChoice = GameChoice.valueOf(new String(payload.asBytes(), UTF_8));
    //            }

    // private val connectionLifecycleCallback: ConnectionLifecycleCallback =
//        object : ConnectionLifecycleCallback()


    private val ReceiveBytesPayloadListener : PayloadCallback =
        object : PayloadCallback()
        {
            override fun onPayloadReceived(endpointId: String, payload: Payload) {
                // This always gets the full data of the payload. Is null if it's not a BYTES payload.
                if (payload.type == Payload.Type.BYTES) {
                    val receivedBytes = payload.asBytes()
                    // mf
                    //val received: BlahList = Json.decodeFromString(receivedBytes.toString())
                    //masterBlah.addAll(received)
                    // mf end
                }
            }



            override fun onPayloadTransferUpdate(
                endpointId: String,
                update: PayloadTransferUpdate
            ) {
                // Bytes payloads are sent as a single chunk, so you'll receive a SUCCESS update immediately
                // after the call to onPayloadReceived().
            }
        }


    //
    // Senders and Receivers both can expect the PayloadCallback.onPayloadTransferUpdate() callback to be invoked to update them about the progress of outgoing and incoming Payloads, respectively.
    //
    // The connections established are full-duplex, which means that Advertisers and Discoverers can simultaneously send and receive Payloads.
    // ---------------------------------------------------------------------------------
    // Disconnecting
    // ---------------------------------------------------------------------------------
    // Finally, disconnectFromEndpoint() disconnects from a particular remote endpoint, and stopAllEndpoints() disconnects from all connected endpoints. Remote endpoints are notified of disconnection via the ConnectionLifecycleCallback.onDisconnected().


    // --------------------------------------------------------------
    // permissions
    //---------------------------------------------------------------
    // Since ACCESS_FINE_LOCATION, BLUETOOTH_ADVERTISE, BLUETOOTH_CONNECT, BLUETOOTH_SCAN
    // and READ_EXTERNAL_STORAGE are considered to be dangerous system permissions,
    // in addition to adding them to your manifest, you must request these permissions at runtime
    // Without permissions, startAdvertising() and startDiscovery() just crash
    // I added the older dangerous permissions as well: BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_WIFI_STATE, CHANGE_WIFI_STATE,


    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        )
        { isGranted: Boolean ->
            if (isGranted) {
                Log.d(TAG, "requestPermissionLauncher")
            } else {

                Toast.makeText(this, R.string.error_missing_permissions, Toast.LENGTH_LONG).show()
                // TODO: 'needs permission' notice given when request ignored by activity
                // should be given when user has denied permission

            }
        }


    private fun checkForPermission() {

        val askForThesePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf(
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION,

                )
        } else {
            listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        }



        for (thisPermission in askForThesePermissions) {

            if (checkSelfPermission(thisPermission)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "\nok: $thisPermission")
            } else {
                Log.d(TAG, "\nrequested permission for $thisPermission")
                requestPermissionLauncher.launch(thisPermission)

            }
        }
    }

}




// --------------------------------------------------------------
// ui
//---------------------------------------------------------------



@Preview(showBackground = true,)
@Composable
fun DefaultPreview() {
    SmellTheme {
        ComposeNavigation()
    }
}


