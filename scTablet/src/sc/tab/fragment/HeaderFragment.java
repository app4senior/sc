package sc.tab.fragment;

import sc.tab.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeaderFragment extends BaseFragment {
	public HeaderFragment() {
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_contentView = inflater.inflate(R.layout.main_header, null);
		return m_contentView;
	}
	
	@Override public Integer id() {
	    return FragmentId.MAIN_HEADER;
    }

}
