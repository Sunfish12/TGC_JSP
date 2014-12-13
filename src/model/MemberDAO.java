package model;

import java.io.InputStream;
import java.util.List;

public interface MemberDAO {
	public abstract MemberBean findByPrimeKey(String username);

	public abstract List<MemberBean> getAll();

	public abstract MemberBean insert(MemberBean bean, InputStream is, long size, String filename);

	public abstract MemberBean update(MemberBean bean, InputStream is, long size, String filename);

	public abstract boolean delete(String Username);
}
