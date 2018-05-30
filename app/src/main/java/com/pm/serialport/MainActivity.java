package com.pm.serialport;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText mEtluxian,mEtDengZhu,mEtColor;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        init();
    }

    private void init() {
        mEtluxian=(EditText)findViewById(R.id.et_xianlu);
        mEtDengZhu=(EditText)findViewById(R.id.et_dengzhu);
        mEtColor=(EditText)findViewById(R.id.et_color);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
