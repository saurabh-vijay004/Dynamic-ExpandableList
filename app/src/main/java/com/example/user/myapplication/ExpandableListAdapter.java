package com.example.user.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 1/16/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable{

    private Context mContext;
    private List<String> mListDataHeader; // header titles
//    private ArrayList<ExpandableListParentClass<Object>> mParent;
    // child data in format of header title, child title
    private HashMap<String, List<String>> mListDataChild;


    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String,List<String>> listDataChild){

        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listDataChild;
    }


    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();
        List childList = mListDataChild.get(mListDataHeader.get(groupPosition));
        System.out.println("childList:::" + childList);
        if (childList != null && ! childList.isEmpty()) {
            return childList.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
