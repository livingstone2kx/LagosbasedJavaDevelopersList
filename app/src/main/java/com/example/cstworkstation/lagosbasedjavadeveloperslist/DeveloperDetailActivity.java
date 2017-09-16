package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CST Workstation on 9/16/2017.
 */

public class DeveloperDetailActivity extends AppCompatActivity {

    public class BookDetailActivity extends AppCompatActivity {
        private ImageView ivProfilePic;
        private TextView tvDeveloperName;
        private TextView tvDeveloperUsername;
        private TextView tvFollowers;
        private TextView tvFollowings;
        private TextView tvRepositories;
        private DeveloperClient client;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_developer_detail);
            // Fetch views
            ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
            tvDeveloperName = (TextView) findViewById(R.id.tvDeveloperName);
            tvDeveloperUsername = (TextView) findViewById(R.id.tvDeveloperUsername);
            tvFollowers = (TextView) findViewById(R.id.tvFollowers);
            tvFollowings = (TextView) findViewById(R.id.tvFollowings);
            tvRepositories = (TextView) findViewById(R.id.tvRepositories);
            // Use the book to populate the data into our views
            Developer developer = (Developer) getIntent().getSerializableExtra(DeveloperListActivity.DEVELOPER_DETAIL_KEY);
            loadDeveloper(developer);
        }

        // Populate data for the developer
        private void loadDeveloper(Developer developer) {
            //change activity name
            this.setDeveloperName(Developer.getDeveloperName());
            // Populate data
            Picasso.with(this).load(Uri.parse(developer.getProfilePicUrl())).error(R.drawable.ic_noprofilepic).into(ivProfilePic);
            tvDeveloperName.setText(developer.getDeveloperName());
            tvDeveloperUsername.setText(developer.getDeveloperUsername());
            // fetch extra developer data from search API
            client = new DeveloperClient();
            client.getExtraDeveloperDetails(developer.getGitHubUsername(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                    try {
                        if (response.has("followers")) {
                            // display number of followers
                            final int numFollowers = follower.length();
                            final String[] followers = new String[numFollowers];
                            for (int i = 0; i < numFollowers; ++i) {
                                followers[i] = follower.getString(i);
                            }
                            tvFollowers.setText(TextUtils.join(", ", followers));
                        }
                        if (response.has("following")) {
                            // display number of followings
                            final int numFollowings = following.length();
                            final String[] followings = new String[numFollowings];
                            for (int i = 0; i < numFollowings; ++i) {
                                followings[i] = following.getString(i);
                            }
                            tvFollowings.setText(TextUtils.join(", ", followings));
                        }
                        if (response.has("repositories")) {
                            tvRepositories.setText(Integer.toString(response.getInt("nrepositories")) + " repositories");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_book_detail, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_share) {
                return true;
            }

            return super.onOptionsItemSelected(item);

            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if (id == R.id.action_share) {
                setShareIntent();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void setShareIntent() {
            ImageView ivImage = (ImageView) findViewById(R.id.ivProfilePic);
            final TextView tvDeveloperName = (TextView)findViewById(R.id.tvDeveloperName);
            // Get access to the URI for the bitmap
            Uri bmpUri = getLocalBitmapUri(ivImage);
            // Construct a ShareIntent with link to developer profile
            Intent shareIntent = new Intent();
            // Construct a ShareIntent with link to developer profile
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @" + (String) tvDeveloperUsername");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, (String)tvDeveloperName.getText());
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            // Launch share menu
            startActivity(Intent.createChooser(shareIntent, "Share Profile"));
        }

        // Returns the URI path to the Bitmap displayed in cover imageview
        public Uri getLocalBitmapUri(ImageView imageView) {
            // Extract Bitmap from ImageView drawable
            Drawable drawable = imageView.getDrawable();
            Bitmap bmp = null;
            if (drawable instanceof BitmapDrawable){
                bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            } else {
                return null;
            }
            // Store image to default external storage directory
            Uri bmpUri = null;
            try {
                File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "share_image_" + System.currentTimeMillis() + ".png");
                file.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }



    }
}
