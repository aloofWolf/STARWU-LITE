package starwu.lite.util.packages.filter;

/**
 * 
 * @ClassName: PackageUtilFilter
 * @Description: PackageUtil过滤器
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public interface PackageUtilFilter {

	/**
	 * 
	 * @Title: doFilter
	 * @Description: 获取包下的所有class类时,过滤掉指定cls
	 * @param cls
	 * @return
	 */
	public boolean doFilter(Class<?> cls);

}
