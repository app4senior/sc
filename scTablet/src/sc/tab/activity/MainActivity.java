package sc.tab.activity;

import java.util.HashMap;

import sc.tab.R;
import sc.tab.fragment.BaseFragment;
import sc.tab.fragment.ContentFragment;
import sc.tab.fragment.SendToWXFragment;
import sc.tab.util.IntentExtrasKeys;
import sc.tab.util.SCContants;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class MainActivity extends Activity implements IWXAPIEventHandler {
	private HashMap<Integer, BaseFragment> m_fragments = new HashMap<Integer, BaseFragment>();
	private IWXAPI m_api;
	
	// getters
	public IWXAPI api() { return m_api; }
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		if (savedInstanceState == null) {
			FragmentTransaction ft = fragmentTrans();
			ft.add(R.id.main_content, new ContentFragment());
            ft.commit();
		}
		m_api = WXAPIFactory.createWXAPI(this, SCContants.APP_ID);
		m_api.registerApp(SCContants.APP_ID);
        m_api.handleIntent(getIntent(), this);
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
    
    public void startSendToWXFragment(Intent intent, BaseFragment sourceFragment) {
        addSourceInfo(intent, sourceFragment);
        SendToWXFragment newFragment = new SendToWXFragment();
        newFragment.init(intent);
        replaceFragment(newFragment, R.id.main_content);
    }
	
	public void sentToWX(String textMsg) {
		WXTextObject textObj = new WXTextObject();
		textObj.text = textMsg;

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = textMsg;

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		
		m_api.sendReq(req);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	@Override protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        m_api.handleIntent(intent, this);
	}

	@Override public void onReq(BaseReq req) {
	}

	@Override public void onResp(BaseResp resp) {
		int result = 0;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
}
