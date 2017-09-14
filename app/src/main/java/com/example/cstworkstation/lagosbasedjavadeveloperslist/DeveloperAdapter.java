package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by CST Workstation on 9/12/2017.
 */

public class DeveloperAdapter extends ArrayAdapter<Developer> {

    //View lookup cache
    private static class ViewHolder{
        public ImageView idProfilePicture;
        public TextView DeveloperUsername;
        public TextView DeveloperName;

    }

    public DeveloperAdapter(Context context, ArrayList<Developer> aDevelopers) {
        super(context, 0, aDevelopers);
    }

    // Translates a particular 'Developer' given a position
    // into a relevant row within AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Developer developer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_developer, parent, false);
            viewHolder.idProfilePicture = (ImageView)convertView.findViewById(R.id.idProfilePicture);
            viewHolder.DeveloperUsername = (TextView)convertView.findViewById(R.id.developerUsername);
            viewHolder.DeveloperName = (TextView)convertView.findViewById(R.id.developerName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.DeveloperUsername.setText(developer.getDeveloperUsername());
        viewHolder.DeveloperName.setText(developer.getDeveloperName());
        Picasso.with(getContext()).load(Uri.parse(developer.getDeveloperProfileUrl())).error(R.drawable.ic_noprofilepic).into(viewHolder.idProfilePicture);
        // Return the completed view to render on screen
        return convertView;
    }
}
