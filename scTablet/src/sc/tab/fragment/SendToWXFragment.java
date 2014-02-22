package sc.tab.fragment;

import sc.tab.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SendToWXFragment extends BaseFragment {
	
	public SendToWXFragment() {
	}
	
	public void init(Intent v) {
		intent(v);
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_contentView = inflater.inflate(R.layout.main_sendtowx, null);
		((Button)findViewById(R.id.senttowx_btn)).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				String msg = ((EditText)findViewById(R.id.sendtowx_txt)).getText().toString();
				activity().sentToWX(msg);
			}
		}); 	
		return m_contentView;
	}

	@Override public Integer id() {
	    return FragmentId.MAIN_SENDTOWX;
    }
}
