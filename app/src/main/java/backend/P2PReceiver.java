package backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.Toast;

import com.eslbuddy.juanmartinez.eslbuddy.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class P2PReceiver extends BroadcastReceiver {

    //Activity
    WearableActivity activity;

    //Channel and manager
    private WifiP2pManager.Channel channel;
    private WifiP2pManager manager;

    //Listener
    private WifiP2pManager.PeerListListener peerListListener;

    //Peers
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    public P2PReceiver (WearableActivity activity, WifiP2pManager.Channel channel, WifiP2pManager manager){
        this.activity = activity;
        this.channel = channel;
        this.manager = manager;
        setListener();
        discoverPeers();
    }

    private void setListener() {

        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {
                List<WifiP2pDevice> refreshedPeers = (List<WifiP2pDevice>) peerList.getDeviceList();
                if (!refreshedPeers.equals(peers)) {
                    peers.clear();
                    peers.addAll(refreshedPeers);

                    // If an AdapterView is backed by this data, notify it
                    // of the change. For instance, if you have a ListView of
                    // available peers, trigger an update.
                    //((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
                    for(WifiP2pDevice device : peers){
                        Log.d("Debug", "Peer: " + device.deviceName);
                    }

                    // Perform any other updates needed based on the new list of
                    // peers connected to the Wi-Fi P2P network.
                }

                if (peers.size() == 0) {
                    Log.d("Debug", "No devices found");
                    return;
                }
            }
        };
    }

    public void discoverPeers(){
        Log.d("Debug", "Trying to start discovery");
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
                Log.d("Debug", "Started discovery");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
            }
        });
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Debug", "Got something from the broadcast receiver");
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Enabled", Toast.LENGTH_SHORT).show();
                //activity.setIsWifiP2pEnabled(true);
            } else {
                Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show();
                //activity.setIsWifiP2pEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed! We should probably do something about
            // that.
            manager.requestPeers(channel, peerListListener);

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //this device details have changed.

        }
    }
}
