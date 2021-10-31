package mx.ralvarez20.udp.utils;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.Arrays;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WoL {

    private final byte[] data;
    private final Context ctx;

    public WoL(Context context, String macAddr){
        this.ctx = context;
        StringBuilder dataPacket = new StringBuilder();
        for (int i = 0; i < 6; i++)
            dataPacket.append("ff");

        for (int i = 0; i < 16; i++)
            dataPacket.append(macAddr);

        data = stringToHex(dataPacket.toString());

        System.out.println("Mostrando MAC Repetida");
        StringBuilder sb = new StringBuilder(data.length * 2);
        for(byte b: data)
            sb.append(String.format("%02x", b));
        System.out.println(sb.toString());
    }

    private static byte[] stringToHex(String mac){
        byte[] biBytes = new BigInteger("10" + mac.replaceAll("\\s", ""), 16).toByteArray();
        return Arrays.copyOfRange(biBytes, 1, biBytes.length);
    }

    public void sendPacket() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            DatagramSocket dSock = null;

            try {
                dSock = new DatagramSocket();
                DatagramPacket dp;
                dp = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 9);
                dSock.setBroadcast(true);
                dSock.send(dp);
            }catch (Exception e){
                Log.e("SOCKET", "sendPacket: " + e.getLocalizedMessage());
            }finally {
                dSock.close();
            }

            handler.post(() -> {
                Toast.makeText(this.ctx, "Se ha enviado el paquete", Toast.LENGTH_SHORT).show();
            });
        });

    }
}