public class Pair<F,S>{
	private final F first;
	private final S second;
	
	public Pair(F setFirst, S setSecond){
		this.first = setFirst;
		this.second = setSecond;
	}
	
	public F getFirst(){
		return first;
	}
	
	public S getSecond(){
		return second;
	}
}