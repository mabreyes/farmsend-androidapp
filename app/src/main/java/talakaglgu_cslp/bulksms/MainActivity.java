package talakaglgu_cslp.bulksms;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by
 * Marvin Kao
 * on 1/26/2018.
 */

public class MainActivity extends Activity {
    private final static int SEND_PERMISSION_REQUEST_CODE=111;
    private String msg="";
    private String pnum="";
    private String ip = "";
    private String category="";
    private  String field="";
    private String sim = "";
    private Context context = this;
    private TextView txt_config;
    private TextView txt_totsmscount;
    private TextView txt_pnum;
    private TextView txt_smsstatus;
    private TextView txt_smscount;
    private TextView txt_appstatus;
    private Button btn_send;
    private EditText input_ip;
    private EditText input_msg;
    private ImageButton btn_barangay;
    private ImageButton btn_crop;
    private ImageButton btn_livestock;
    private ImageButton btn_status;
    private ImageButton btn_gender;
    private ImageButton btn_insurance;
    private ImageView btn_about;
    private int index = 0;
    private int smscnt = 1;
    private int resend = 0;
    private int smstotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_config = (TextView) findViewById(R.id.txt_config);
        txt_totsmscount = (TextView) findViewById(R.id.txt_totsmscount);
        txt_pnum =(TextView) findViewById(R.id.txt_pnum);
        txt_smsstatus =(TextView) findViewById(R.id.txt_smsstatus);
        txt_smscount = (TextView) findViewById(R.id.txt_smscount);
        txt_appstatus = (TextView) findViewById(R.id.txt_appstatus);
        input_ip = (EditText)findViewById(R.id.input_ip);
        input_msg = (EditText)findViewById(R.id.input_msg);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_barangay = (ImageButton) findViewById(R.id.btn_barangay);
        btn_crop = (ImageButton) findViewById(R.id.btn_crop);
        btn_livestock = (ImageButton) findViewById(R.id.btn_livestock);
        btn_status = (ImageButton) findViewById(R.id.btn_status);
        btn_gender = (ImageButton) findViewById(R.id.btn_gender);
        btn_insurance = (ImageButton) findViewById(R.id.btn_insurance);
        btn_about = (ImageView) findViewById(R.id.btn_about);
        btn_about.setClickable(true);
        if(checkPermission(Manifest.permission.SEND_SMS)) {
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS}, SEND_PERMISSION_REQUEST_CODE);
        }
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutdialog();
            }
        });
        btn_barangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="barangay";
                barangaydialog();
            }
        });
        btn_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="crop";
                cropdialog();
            }
        });
        btn_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="livestock";
                livestockdialog();
            }
        });
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="status";
                statusdialog();
            }
        });
        btn_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="gender";
                genderdialog();
            }
        });
        btn_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="insurance";
                insurancedialog();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = input_ip.getText().toString();
                msg = input_msg.getText().toString();
                senddialog();
            }
        });
    }

    private void aboutdialog() {
        AlertDialog.Builder alertabout = new AlertDialog.Builder(MainActivity.this);
        alertabout.setMessage("FarmSend v1.1\nCreated by:\nRicky Bantonare\nMarvin Kao\nKathrina Ira Mitchell\nRomel Niño Paano\nMarc Anthony Reyes\nJulius Urot").setCancelable(true);
        AlertDialog alertinfo = alertabout.create();
        alertinfo.setTitle("About");
        alertinfo.show();
    }

    private void insurancedialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.insurance, null);
        AlertDialog.Builder alertinsurance = new AlertDialog.Builder(MainActivity.this);
        alertinsurance.setView(view);
        Button btn_cropinsurance = (Button) view.findViewById(R.id.btn_cropinsurance);
        Button btn_livestockinsurance = (Button) view.findViewById(R.id.btn_livestockinsurance);
        alertinsurance.setCancelable(true);
        final Dialog insurancedialog = alertinsurance.create();
        insurancedialog.show();
        btn_cropinsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "crop";
                insurancedialog.cancel();
            }
        });
        btn_livestockinsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "livestock";
                insurancedialog.cancel();
            }
        });
    }

    private void genderdialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.gender, null);
        AlertDialog.Builder alertgender = new AlertDialog.Builder(MainActivity.this);
        alertgender.setView(view);
        Button btn_female = (Button) view.findViewById(R.id.btn_female);
        Button btn_male = (Button) view.findViewById(R.id.btn_male);
        alertgender.setCancelable(true);
        final Dialog genderdialog = alertgender.create();
        genderdialog.show();
        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "female";
                genderdialog.cancel();
            }
        });
        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "male";
                genderdialog.cancel();
            }
        });
    }

    private void statusdialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.status, null);
        AlertDialog.Builder alertstatus = new AlertDialog.Builder(MainActivity.this);
        alertstatus.setView(view);
        Button btn_common = (Button) view.findViewById(R.id.btn_common);
        Button btn_divorced = (Button) view.findViewById(R.id.btn_divorced);
        Button btn_married = (Button) view.findViewById(R.id.btn_married);
        Button btn_single = (Button) view.findViewById(R.id.btn_single);
        Button btn_widowed = (Button) view.findViewById(R.id.btn_widowed);
        alertstatus.setCancelable(true);
        final Dialog statusdialog = alertstatus.create();
        statusdialog.show();
        btn_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "common";
                statusdialog.cancel();
            }
        });
        btn_divorced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "divorced";
                statusdialog.cancel();
            }
        });
        btn_married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "married";
                statusdialog.cancel();
            }
        });
        btn_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "single";
                statusdialog.cancel();
            }
        });
        btn_widowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "widowed";
                statusdialog.cancel();
            }
        });
    }

    private void livestockdialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.livestock, null);
        AlertDialog.Builder alertlivestock = new AlertDialog.Builder(MainActivity.this);
        alertlivestock.setView(view);
        Button btn_carabao = (Button) view.findViewById(R.id.btn_carabao);
        Button btn_cattle = (Button) view.findViewById(R.id.btn_cattle);
        Button btn_goat = (Button) view.findViewById(R.id.btn_goat);
        Button btn_horse = (Button) view.findViewById(R.id.btn_horse);
        Button btn_poultry = (Button) view.findViewById(R.id.btn_poultry);
        Button btn_swine = (Button) view.findViewById(R.id.btn_swine);
        alertlivestock.setCancelable(true);
        final Dialog livestockdialog = alertlivestock.create();
        livestockdialog.show();
        btn_carabao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "carabao";
                livestockdialog.cancel();
            }
        });
        btn_cattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "cattle";
                livestockdialog.cancel();
            }
        });
        btn_goat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "goat";
                livestockdialog.cancel();
            }
        });
        btn_horse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "horse";
                livestockdialog.cancel();
            }
        });
        btn_poultry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "poultry";
                livestockdialog.cancel();
            }
        });
        btn_swine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "swine";
                livestockdialog.cancel();
            }
        });

    }

    private void cropdialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.crop, null);
        AlertDialog.Builder alertcrop = new AlertDialog.Builder(MainActivity.this);
        alertcrop.setView(view);
        Button btn_corn = (Button) view.findViewById(R.id.btn_corn);
        Button btn_rice = (Button) view.findViewById(R.id.btn_rice);
        alertcrop.setCancelable(true);
        final Dialog cropdialog = alertcrop.create();
        cropdialog.show();
        btn_corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "corn";
                cropdialog.cancel();
            }
        });
        btn_rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "rice";
                cropdialog.cancel();
            }
        });
    }

    private void barangaydialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.barangay, null);
        AlertDialog.Builder alertbarangay = new AlertDialog.Builder(MainActivity.this);
        alertbarangay.setView(view);
        Button btn_barangay1 = (Button) view.findViewById(R.id.btn_barangay1);
        Button btn_barangay2 = (Button) view.findViewById(R.id.btn_barangay2);
        Button btn_barangay3 = (Button) view.findViewById(R.id.btn_barangay3);
        Button btn_barangay4 = (Button) view.findViewById(R.id.btn_barangay4);
        Button btn_barangay5 = (Button) view.findViewById(R.id.btn_barangay5);
        Button btn_basak = (Button) view.findViewById(R.id.btn_basak);
        Button btn_baylanan = (Button) view.findViewById(R.id.btn_baylanan);
        Button btn_cacaon = (Button) view.findViewById(R.id.btn_cacaon);
        Button btn_colawingon = (Button) view.findViewById(R.id.btn_colawingon);
        Button btn_cosina = (Button) view.findViewById(R.id.btn_cosina);
        Button btn_dagumbaan = (Button) view.findViewById(R.id.btn_dagumbaan);
        Button btn_dominorog = (Button) view.findViewById(R.id.btn_dominorog);
        Button btn_indulang = (Button) view.findViewById(R.id.btn_indulang);
        Button btn_lantod = (Button) view.findViewById(R.id.btn_lantod);
        Button btn_lapok = (Button) view.findViewById(R.id.btn_lapok);
        Button btn_liguron = (Button) view.findViewById(R.id.btn_liguron);
        Button btn_lingion = (Button) view.findViewById(R.id.btn_lingion);
        Button btn_lirongan = (Button) view.findViewById(R.id.btn_lirongan);
        Button btn_miarayon= (Button) view.findViewById(R.id.btn_miarayon);
        Button btn_sagaran= (Button) view.findViewById(R.id.btn_sagaran);
        Button btn_salucot= (Button) view.findViewById(R.id.btn_salucot);
        Button btn_sanantonio= (Button) view.findViewById(R.id. btn_sanantonio);
        Button btn_sanisidro= (Button) view.findViewById(R.id.btn_sanisidro);
        Button btn_sanmiguel= (Button) view.findViewById(R.id.btn_sanmiguel);
        Button btn_sanrafael= (Button) view.findViewById(R.id.btn_sanrafael);
        Button btn_santonino= (Button) view.findViewById(R.id.btn_santonino);
        Button btn_tagbak= (Button) view.findViewById(R.id.btn_tagbak);
        Button btn_tikalaan= (Button) view.findViewById(R.id.btn_tikalaan);
        alertbarangay.setCancelable(true);
        final Dialog brgydialog = alertbarangay.create();
        brgydialog.show();
        btn_barangay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "barangay1";
                brgydialog.cancel();
            }
        });
        btn_barangay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "barangay2";
                brgydialog.cancel();
            }
        });
        btn_barangay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "barangay3";
                brgydialog.cancel();
            }
        });
        btn_barangay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "barangay4";
                brgydialog.cancel();
            }
        });
        btn_barangay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "barangay5";
                brgydialog.cancel();
            }
        });
        btn_basak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "basak";
                brgydialog.cancel();
            }
        });
        btn_baylanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "baylanan";
                brgydialog.cancel();
            }
        });
        btn_cacaon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "cacaon";
                brgydialog.cancel();
            }
        });
        btn_colawingon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "colawingon";
                brgydialog.cancel();
            }
        });
        btn_cosina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "cosina";
                brgydialog.cancel();
            }
        });
        btn_dagumbaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "dagumbaan";
                brgydialog.cancel();
            }
        });
        btn_dominorog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "dominorog";
                brgydialog.cancel();
            }
        });
        btn_indulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "indulang";
                brgydialog.cancel();
            }
        });
        btn_lantod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "lantod";
                brgydialog.cancel();
            }
        });
        btn_lapok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "lapok";
                brgydialog.cancel();
            }
        });
        btn_liguron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "liguron";
                brgydialog.cancel();
            }
        });
        btn_lingion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "lingion";
                brgydialog.cancel();
            }
        });
        btn_lirongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "lirongan";
                brgydialog.cancel();
            }
        });
        btn_miarayon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "miarayon";
                brgydialog.cancel();
            }
        });
        btn_sagaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "sagaran";
                brgydialog.cancel();
            }
        });
        btn_salucot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "salucot";
                brgydialog.cancel();
            }
        });
        btn_sanantonio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "sanantonio";
                brgydialog.cancel();
            }
        });
        btn_sanisidro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "sanisidro";
                brgydialog.cancel();
            }
        });
        btn_sanmiguel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "sanmiguel";
                brgydialog.cancel();
            }
        });
        btn_sanrafael.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "sanrafael";
                brgydialog.cancel();
            }
        });
        btn_santonino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "santonino";
                brgydialog.cancel();
            }
        });
        btn_tagbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "tagbak";
                brgydialog.cancel();
            }
        });
        btn_tikalaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field = "tikalaan";
                brgydialog.cancel();
            }
        });



    }

    private void senddialog() {
        if(ip=="" || msg == "" || category == "" || field == ""){
            AlertDialog.Builder alertempty = new AlertDialog.Builder(MainActivity.this);
            alertempty.setMessage("Please try again and input valid data.").setCancelable(true);
            AlertDialog alert = alertempty.create();
            alert.setTitle("Error");
            alert.show();
        }
        else if(msg.length()>149){
            AlertDialog.Builder alertempty = new AlertDialog.Builder(MainActivity.this);
            alertempty.setMessage("Please try again and input less than 150 chars in your message.").setCancelable(true);
            AlertDialog alert = alertempty.create();
            alert.setTitle("Error");
            alert.show();
        }
        else {
            AlertDialog.Builder alertsend = new AlertDialog.Builder(MainActivity.this);
            alertsend.setMessage("IP ADDRESS:" + ip + "\nMessage:" + msg + "\nContacts:" + category + ":" + field + "\nDo you wish to send with the info above?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    simdialog();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert = alertsend.create();
            alert.setTitle("Confirmation");
            alert.show();
        }
    }

    private void simdialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.sim, null);
        AlertDialog.Builder alertsim = new AlertDialog.Builder(MainActivity.this);
        alertsim.setView(view);
        Button btn_globe = (Button) view.findViewById(R.id.btn_globe);
        Button btn_smart = (Button) view.findViewById(R.id.btn_smart);
        Button btn_sun = (Button) view.findViewById(R.id.btn_sun);
        Button btn_all = (Button) view.findViewById(R.id.btn_all);
        alertsim.setCancelable(true);
        final Dialog simdialog = alertsim.create();
        simdialog.show();
        btn_globe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sim = "globe";
                simdialog.cancel();
                connect();
            }
        });
        btn_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sim = "sun";
                simdialog.cancel();
                connect();
            }
        });
        btn_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sim = "smart";
                simdialog.cancel();
                connect();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sim = "all";
                simdialog.cancel();
                connect();
            }
        });
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }


    private void connect(){
            ip = input_ip.getText().toString();
            msg = input_msg.getText().toString();
            txt_config.setText("CONFIGURE CONNECTION: ("+ip+")");
            txt_appstatus.setText("Server: Busy"+sim);
            txt_smsstatus.setText("SMS Status:Sending...");
            index = 0;
            resend= 0;
            smstotal = 0;
            smscnt = 1;
            connectpnum();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendSMS();
            }
        }, 2000);

    }

    public void getDataP(String result){
        pnum = result;
    }

    private void connectpnum() {
        backgroundTaskP bgp = new backgroundTaskP(this);
        bgp.execute(ip,category,field,sim);

    }

    private void sendSMS() {
        final String[] phoneNumber = pnum.split("s");
        smstotal = phoneNumber.length;
        String SENT = "SMS_SENT";
        final PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(
                SENT), 0);
        final SmsManager sms = SmsManager.getDefault();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(index<smstotal) {
                    txt_smsstatus.setText("SMS Status: Sending...");
                    txt_smscount.setText("Sms Count: "+smscnt);
                    txt_totsmscount.setText("Total Sms Count: "+smstotal);
                    txt_pnum.setText("Phone Number: "+phoneNumber[index]);
                    sms.sendTextMessage(phoneNumber[index], null, msg, sentPI, null);
                    handler.postDelayed(this,5000);
                }
                else {
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        txt_smsstatus.setText("SMS Status: SMS Sent!");
                        index++;
                        smscnt++;
                        resend=0;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        txt_smsstatus.setText("SMS Status: SMS Failed! Retrying...");
                        if(resend==2){
                            index++;
                        }
                        else {
                            resend++;
                        }
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        txt_smsstatus.setText("Message Status: No Service! Retrying...");
                        if(resend==2){
                            index++;
                        }
                        else {
                            resend++;
                        }
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        txt_smsstatus.setText("Message Status: Null PDU! Retrying...");
                        if(resend==2){
                            index++;
                        }
                        else {
                            resend++;
                        }
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        txt_smsstatus.setText("Message Status: Radio off! Retrying...");
                        if(resend==2){
                            index++;
                        }
                        else {
                            resend++;
                        }
                        break;
                }
            }
        }, new IntentFilter(SENT));
    }
}

