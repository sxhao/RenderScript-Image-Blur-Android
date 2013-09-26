package com.example.newsupdate;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DisplayFragment extends Fragment {
	private ListView lstView;
	private TextView txtDisplay;
	private RequestQueue volleyQueue;
	private ArrayList<NewsModel> arrNews;
	private LayoutInflater lf;
	private VolleyAdapter va;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_fragment, null);
		txtDisplay = (TextView) v.findViewById(R.id.response);
		lf = LayoutInflater.from(getActivity());
		arrNews = new ArrayList<NewsModel>();
		va = new VolleyAdapter();
		lstView = (ListView) v.findViewById(R.id.listView);
		lstView.setAdapter(va);
		String url = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
		volleyQueue = Volley.newRequestQueue(getActivity());
		GetJson getjson = new GetJson();
		volleyQueue.add(new JsonObjectRequest(Method.GET, url, null, getjson,
				getjson));

		return v;
	}
/*
 * (non-Javadoc)
 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	class GetJson implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onResponse(JSONObject response) {
			// TODO Auto-generated method stub
			txtDisplay.setText(response.toString());
			parseJSON(response);
			va.notifyDataSetChanged();
			getView().findViewById(R.id.progressBar1).setVisibility(View.GONE);
		}

	}

	private void parseJSON(JSONObject json) {
		try {
			JSONObject value = json.getJSONObject("value");
			JSONArray items = value.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {

				JSONObject item = items.getJSONObject(i);
				NewsModel nm = new NewsModel();
				nm.setTitle(item.optString("title"));
				nm.setDescription(item.optString("description"));
				nm.setLink(item.optString("link"));
				nm.setPubDate(item.optString("pubDate"));
				arrNews.add(nm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class NewsModel {
		private String title;
		private String link;
		private String description;
		private String pubDate;

		void setTitle(String title) {
			this.title = title;
		}

		void setLink(String link) {
			this.link = link;
		}

		void setDescription(String description) {
			this.description = description;
		}

		void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}

		String getLink() {
			return link;
		}

		String getDescription() {
			return description;
		}

		String getPubDate() {
			return pubDate;
		}

		String getTitle() {

			return title;
		}
	}

	class VolleyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrNews.size();
		}

		@Override
		public Object getItem(int i) {
			return arrNews.get(i);
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder vh;
			if (view == null) {
				String fontPath = "DroidSerif.ttf";
				vh = new ViewHolder();
				view = lf.inflate(R.layout.row_listview, null);
				vh.tvTitle = (TextView) view.findViewById(R.id.txtTitle);
				vh.tvDesc = (TextView) view.findViewById(R.id.txtDesc);
				vh.tvDate = (TextView) view.findViewById(R.id.txtDate);
				// get the font face
				Typeface tf = Typeface.createFromAsset(getResources()
						.getAssets(), fontPath);

				// Apply the font
				vh.tvTitle.setTypeface(tf);
				vh.tvDesc.setTypeface(tf);
				vh.tvDate.setTypeface(tf);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			NewsModel nm = arrNews.get(i);
			vh.tvTitle.setText(nm.getTitle());
			vh.tvDesc.setText(nm.getDescription());
			vh.tvDate.setText(nm.getPubDate());
			return view;
		}

		class ViewHolder {
			TextView tvTitle;
			TextView tvDesc;
			TextView tvDate;

		}

	}
}
