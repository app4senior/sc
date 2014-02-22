package sc.tab.fragment;

import sc.tab.activity.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;

/* base class for all fragments in MainActivity */
public abstract class BaseFragment extends Fragment {
	private MainActivity m_activity;
	protected View m_contentView;
	private Intent m_intent;

	// getters	
	public abstract Integer id();
	public Intent getIntent() { return m_intent; }
	
	// setters
	protected void intent(Intent v) { m_intent = v; }
	
	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		m_activity = (MainActivity) activity;
	}
	
	@Override public void onDetach() {
		super.onDetach();
		m_activity.removeFragment(this);
	}
	
	protected View findViewById(int id) {
    	return m_contentView.findViewById(id);
    }
    
	protected BaseFragment findFragmentById(Integer id) {
		return m_activity.findFragmentById(id);
	}
	
	public MainActivity activity() {
		return m_activity;
	}
}
