package sc.tab.fragment;

import sc.tab.R;
import sc.tab.util.IntentExtrasKeys;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends BaseFragment {
	public ContentFragment() {
	}
	
	public void init(Intent v) {
		m_intent = v;
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_contentView = inflater.inflate(R.layout.main_content, null);
		if (m_intent != null) {
			String content = m_intent.getStringExtra(IntentExtrasKeys.FRAGMENT_DATA);
			((TextView)findViewById(R.id.content_text)).setText(content);
		}
		return m_contentView;
	}

	@Override public Integer id() {
	    return FragmentId.MAIN_CONTENT;
    }
}
