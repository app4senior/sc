package sc.tab.activity;

import java.util.HashMap;

import sc.tab.R;
import sc.tab.fragment.BaseFragment;
import sc.tab.fragment.ContentFragment;
import sc.tab.util.IntentExtrasKeys;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	private HashMap<Integer, BaseFragment> m_fragments = new HashMap<Integer, BaseFragment>();

	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		if (savedInstanceState == null) {
			FragmentTransaction ft = fragmentTrans();
			ft.add(R.id.main_content, new ContentFragment());
            ft.commit();
		}
	}

	@Override public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof BaseFragment) {
			BaseFragment base = (BaseFragment) fragment;
			m_fragments.put(base.id(), base);
		}
	}
	
	public void removeFragment(BaseFragment baseFragment) {
		m_fragments.remove(baseFragment.id());
	}
	
	public BaseFragment findFragmentById(Integer id) {
		return m_fragments.get(id);
	}
	
	private FragmentTransaction fragmentTrans() {
		return getFragmentManager().beginTransaction();
	}
	
    private void replaceFragment(BaseFragment newFragment, int containerId) {
        FragmentTransaction ft = fragmentTrans();
		ft.replace(containerId, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
	
    private void addSourceInfo(Intent intent, BaseFragment sourceFragment) {
        intent.putExtra(IntentExtrasKeys.SOURCE_FRAGMENT_ID, sourceFragment.id());
    }
	
    public void startContentFragment(Intent intent, BaseFragment sourceFragment) {
        addSourceInfo(intent, sourceFragment);
        ContentFragment newFragment = new ContentFragment();
        newFragment.init(intent);
        replaceFragment(newFragment, R.id.main_content);
    }
}
