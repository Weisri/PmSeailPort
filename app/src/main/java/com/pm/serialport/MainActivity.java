package com.pm.serialport;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pm.serialport.bean.ComBean;
import com.pm.serialport.common.SerialHelper;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class MainActivity extends Activity implements View.OnClickListener {
    private EditText mEtluxian,mEtDengZhu,mEtColor;
    private Button btTest;
    private TextView tvResult;
    private ToggleButton tbCheck;
    private RadioButton rbHex, rbTxt;
    private SerialControl com;
    private SerialPortFinder mSerialPortFinder;
    private List<String> allDevices=new ArrayList();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        //读取串行端口路径
        findPort();
        com=new SerialControl();
        //串行端口路径
        com.setPort("/dev/ttyS2");
        com.setBaudRate("9600");
        //打开串口
        openComPort(com);
    }

    /**
     * 读取串行端口路径
     */
    private void findPort() {
        mSerialPortFinder =new SerialPortFinder();
        try {
            String[] entryValues = mSerialPortFinder.getAllDevicesPath();
            for (int i = 0; i < entryValues.length; i++) {
                allDevices.add(entryValues[i]);
            }
        } catch (Exception e) {
            allDevices.add("/dev/ttyS1");
        }

    }

    /**
     * 开启串口
     * @param ComPort
     */
    private void openComPort(SerialHelper ComPort) {
        try
        {
            ComPort.open();
        } catch (SecurityException e) {
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
        }
    }

    /**
     * 关闭串口
     * @param ComProt
     */
    private void closeComPort(SerialHelper ComProt){
        if (ComProt!=null){
            ComProt.stopSend();
            ComProt.close();
        }

    }

    private void init() {
        mEtluxian=(EditText)findViewById(R.id.et_xianlu);
        mEtDengZhu=(EditText)findViewById(R.id.et_dengzhu);
        mEtColor=(EditText)findViewById(R.id.et_color);
        tbCheck=(ToggleButton)findViewById(R.id.tb_open);
        tvResult=(TextView)findViewById(R.id.tv_result);
        rbHex=(RadioButton)findViewById(R.id.rb_hex);
        rbTxt=(RadioButton)findViewById(R.id.rb_txt);
        btTest=(Button)findViewById(R.id.btn_test);

        btTest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }


    private class SerialControl extends SerialHelper{
        public SerialControl(){
        }

        @Override
        protected void onDataReceived(ComBean ComRecData) {

        }
    }
    private void ShowMessage(String sMsg)
    {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


}
