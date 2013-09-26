package com.example.newsupdate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class EmptyFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_dummy, null);
		ImageView img=(ImageView)v.findViewById(R.id.img);
		RenderScript rs =RenderScript.create(getActivity());
		Context context = getActivity();
		Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
		final Allocation input = Allocation.createFromBitmap( rs, photo, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT );
		final Allocation output = Allocation.createTyped( rs, input.getType() );
		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create( rs, Element.U8_4( rs ) );
		Float myBlurRadius=5f;
		script.setRadius( myBlurRadius);
		script.setInput( input );
		script.forEach( output );
		output.copyTo( photo );
		img.setImageBitmap(photo);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

}
