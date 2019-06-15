package com.jm.projectunion.common.base;

/**
 * Fragment基类
 * 
 * @author Young
 * @date 2015年12月22日 下午2:15:08
 */
public abstract class BaseExtraFragment extends BaseFragment {

	protected boolean reload = false;


	/**
	 * 可见时执行（第一次进入时不执行，适用于切换item，当item可见时调用）
	 */
	public void onVisible() {
	}

	/**
	 * （第一次进入时不执行，适用于切换item，当item可见需要重新加载数据时调用），使用前调用fragment.setReload(true);
	 */
	public void reloadData() {
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
				onVisible();
			if (reload && isPrepared) {
				reloadData();
//				reload = false;
			}
		}
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public boolean getReload() {
		return reload;
	}
}
