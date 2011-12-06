package frappe.kobe;

public class Portal {
	private String mLabel;
	private String mStatus;
	private String mKeyphrase; //keyphase used to verify sender
	private String cheese;
	public int mState;
	
	//portals considered unlocked upon creation
	Portal(String label, String cellnum, String key){
		this.mLabel = label;
		this.mKeyphrase = key;
		this.cheese = cellnum;
		this.mState = 1; //state set to locked
		setStatus(mState);
	}
	
	private void setStatus(int state){
		if(state == -1){
			mStatus = "Unlocked";
		}
		else if(state == 1){
			mStatus = "Locked";
		}
	}
	
	public void toggleState(){
		mState *= -1;
		setStatus(mState);
	}
	

	public String getLabel() {
		return mLabel;
	}
	
	public String getStatus(){
		return mStatus;
	}
	
	public String getCellNumber(){
		return cheese;
	}
	
	public String getKey(){
		return mKeyphrase;
	}
}
