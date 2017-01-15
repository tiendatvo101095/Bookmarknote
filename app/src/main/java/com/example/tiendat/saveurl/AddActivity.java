package com.example.tiendat.saveurl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {

    private ImageButton btn_image;
    private EditText edt_url,edt_name;
    private Button btn_create;
    private Uri imageuri = null;
    private StorageReference storage;
    private DatabaseReference mDatabase;

    private ProgressDialog progressdialog;

    private static final int GALLERY_REQUEST =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        storage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bookmarks");

        btn_image = (ImageButton) findViewById(R.id.imageButton);
        edt_url = (EditText) findViewById(R.id.edtUrl);
        edt_name =(EditText) findViewById(R.id.edtName);
        btn_create = (Button) findViewById(R.id.btn_add);
        progressdialog = new ProgressDialog(this);

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);


            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAdd();
            }
        });
    }

    public void startAdd() {

        progressdialog.setMessage("Creating...");
        progressdialog.show();

        String url = edt_url.getText().toString().trim();
        String name = edt_name.getText().toString().trim();

        if(!TextUtils.isEmpty(url) && !TextUtils.isEmpty(name) && imageuri != null )
        {
            StorageReference filepath = storage.child("Url_images").child(imageuri.getLastPathSegment());

            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    // Create a new child with a auto-generated ID.
                    DatabaseReference childRef = mDatabase.push();

                    // Set the child's data to the value passed in from the text box.
                    childRef.child("Url").setValue(edt_url.getText().toString());
                    childRef.child("Name").setValue(edt_name.getText().toString());
                    childRef.child("Image").setValue(downloadUrl.toString());

                    progressdialog.dismiss();

                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }

            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int  resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if( requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data!= null)
        {
            imageuri = data.getData();
            btn_image.setImageURI(imageuri);
        }
    }
}
