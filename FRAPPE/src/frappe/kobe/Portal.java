package frappe.kobe;

public class Portal {
	private String mLabel;
	private String mStatus;
	int mState;
	private String cheese = "3045129108";
	
	Portal(String label, int state){
		this.mLabel = label;
		this.mState = state;
		setStatus(mState);
	}
	
	public void toggleState(){
		mState *= -1;
		setStatus(mState);

	}
	
	private void setStatus(int state){
		if(state == -1){
			mStatus = "unlocked";
		}
		else if(state == 1){
			mStatus = "locked";
		}
	}

	public String getLabel() {
		return mLabel;
	}
	
	public String getStatus(){
		return mStatus;
	}
	
	public String getPhoneNumber(){
		return cheese;
	}
}
