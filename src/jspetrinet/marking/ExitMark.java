package jspetrinet.marking;

class ExitMark {
	private Mark emark;
	private int numOfMark;
	private boolean vanishing;
	
	ExitMark() {
		this.emark = null;
		this.numOfMark = 0;
		this.vanishing = false;
	}
	
	Mark get() {
		return emark;
	}
	
	boolean canVanishing() {
		return vanishing;
//		if (numOfMark == 1) {
//			return true;
//		} else {
//			return false;
//		}
	}
	
	void addMark(Mark self, Mark emark) {
		switch (this.numOfMark) {
		case 0:
			if (self.getGenVec() == emark.getGenVec()) {
				this.emark = emark;
				this.numOfMark = 1;
				this.vanishing = true;
				break;
			} else {
				this.emark = self;
				this.numOfMark = 1;
				this.vanishing = false;
				break;
			}
		case 1:
			if (this.emark != emark) {
				this.emark = null;
				this.numOfMark = 2;
				this.vanishing = false;
			};
			break;
		default:
		}
	}

	void addMark(Mark self, ExitMark other) {
		switch (this.numOfMark) {
		case 0:
			switch (other.numOfMark) {
			case 1:
				if (self.getGenVec() == other.emark.getGenVec()) {
					this.emark = other.emark;
					this.numOfMark = 1;
					this.vanishing = true;
				} else {
					this.emark = self;
					this.numOfMark = 1;
					this.vanishing = false;
				}
				break;
			case 2:
				this.emark = null;
				this.numOfMark = 2;
				this.vanishing = false;
				break;
			default:
			}
			break;
		case 1:
			switch (other.numOfMark) {
			case 1:
				if (this.emark != other.emark) {
					this.emark = null;
					this.numOfMark = 2;
					this.vanishing = false;
				};
				break;
			case 0:
			case 2:
				this.emark = null;
				this.numOfMark = 2;
				this.vanishing = false;
				break;
			default:
			}
			break;
		default:
		}
	}
}

