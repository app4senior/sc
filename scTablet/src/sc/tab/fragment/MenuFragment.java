package sc.tab.fragment;

import sc.tab.R;
import sc.tab.util.IntentExtrasKeys;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends BaseFragment {
	public MenuFragment() {
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_contentView = inflater.inflate(R.layout.main_menu, null);

		((Button)findViewById(R.id.wechat_btn)).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent();
				activity().startSendToWXFragment(intent, MenuFragment.this);
			}
		});
		
		((Button)findViewById(R.id.family_btn)).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(IntentExtrasKeys.FRAGMENT_DATA, "Family is clicked!");
				activity().startContentFragment(intent, MenuFragment.this);
			}
		});

		((Button)findViewById(R.id.tools_btn)).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(IntentExtrasKeys.FRAGMENT_DATA, "Tools is clicked!");
				activity().startContentFragment(intent, MenuFragment.this);
			}
		});

		return m_contentView;
	}
	
	@Override public Integer id() {
	    return FragmentId.MAIN_MENU;
    }
}
