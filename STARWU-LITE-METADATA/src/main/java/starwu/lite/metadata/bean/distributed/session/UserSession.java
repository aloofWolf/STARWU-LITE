package starwu.lite.metadata.bean.distributed.session;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class UserSession<T> implements Serializable {

	private static final long serialVersionUID = -4832274719336557204L;
	private Long userId; // 用户id
	private String userName; // 用户名称
	private Integer deviceType;
	
	private T obj; // 其它信息
	
	public UserSession(Long userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public UserSession(Long userId, String userName,Integer deviceType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.deviceType = deviceType;
	}

	public UserSession(Long userId, String userName, T obj) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.obj = obj;
	}

	public UserSession(Long userId, String userName, Integer deviceType,T obj) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.obj = obj;
		this.deviceType = deviceType;
	}

}
