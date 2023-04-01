package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Button btnLogout;
    final String TAG = "ProfileActivity";
    TextView textViewUserName,textViewUserMob,textViewUserEmail;

    ListView listview;
    String[] mTitle={"MY ADDRESS","HELP","CONTACT US","ABOUT US","CHANGE PASSWORD","LOGOUT"};
    int images[]={R.drawable.myaddress,R.drawable.help,R.drawable.contactus,R.drawable.aboutus,R.drawable.changepass,R.drawable.logout};

    ImageView cover;
    CircleImageView profile;
    FloatingActionButton changeProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_more);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_services:
                        startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_explore:
                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_more:
                        return true;
                }
                return false;
            }
        });

       /* btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MoreActivity.this,MainActivity.class);
            }
        }); */

        getSupportActionBar().setTitle("Profile");

        bindcomponents();
        showProfileDetails();
        profile = findViewById(R.id.profile_image);
        changeProfile = findViewById(R.id.cameraIcon);

        changeProfile.setOnClickListener(v -> ImagePicker.Companion.with(ProfileActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(20));

        listview=findViewById(R.id.listview);

        MyAdapter adapter=new MyAdapter(this,mTitle,images);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position== 0)
                {
                    startActivity(new Intent(getApplicationContext(),MyAddressActivity.class));
                }
                if(position== 1)
                {
                    startActivity(new Intent(getApplicationContext(),HelpActivity.class));

                    //Toast.makeText(ProfileActivity.this,"Manage Services",Toast.LENGTH_SHORT).show();
                }if(position== 2)
                {
                    startActivity(new Intent(getApplicationContext(),ContactUsActivity.class));

                }if(position== 3)
                {
                    Toast.makeText(ProfileActivity.this,"About Us",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),AboutUsActivity.class));

                }if(position== 4)
                    startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
                {
                }if(position== 5)
                {
                    Toast.makeText(ProfileActivity.this,"Logout",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                }
                if(position== 6)
                {
                    Toast.makeText(ProfileActivity.this,"Settings",Toast.LENGTH_SHORT).show();
                }
                if(position== 7)
                {
               }
                if(position== 8)
                {
                 }

            }
        });


    }

    public void bindcomponents(){
        this.textViewUserName = findViewById(R.id.text_profile_name);
        this.textViewUserEmail = findViewById(R.id.text_profile_email);
        this.textViewUserMob = findViewById(R.id.text_profile_mob);
    }

    public void showProfileDetails(){
        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user  = snapshot.getValue(User.class);

                textViewUserEmail.setText(user.getEmail());
                textViewUserMob.setText(user.getPhoneNo());
                textViewUserName.setText(user.getFullName());

                Log.d(TAG, "onDataChange: "+user.getFullName()+user.getEmail()+user.getPhoneNo());
                //getId(userId);
                /*if(id != null){
                   textViewUid.setText(id.getUserId());
                   Log.d("RegAct", "onDataChange: "+ textViewUid.getText().toString());
               }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  Log.d("RegAct", "onDataChange2: "+ textViewUid.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10) {
            assert data != null;
            Uri uri = data.getData();
            cover.setImageURI(uri);

        } else if(requestCode == 20){
            assert data != null;
            Uri uri2 = data.getData();
            profile.setImageURI(uri2);
        }
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter(Context c,String title[],int imgs[]){
            super(c,R.layout.row,R.id.text_profile_name,title);
            this.context=c;
            this.rTitle=title;
            this.rImgs=imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutinflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutinflater.inflate(R.layout.row,parent,false);
            ImageView images=row.findViewById(R.id.image1);
            TextView myTitle=row.findViewById(R.id.text_profile_name);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);

            return row;
        }
    }

}