package com.xt.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xt.base.BaseAction;
import com.xt.entity.Address;
import com.xt.entity.Bill;
import com.xt.entity.Goods;
import com.xt.entity.User;
import com.xt.entity.Wannas;
import com.xt.service.UserService;

@Namespace("/user")
@ParentPackage("json-default")
@Controller
public class UserAction extends BaseAction {

	public User user;
	private Bill bill;
	@Autowired
	private UserService userService;
	private String userid;
	private String code;
	private User loginedUser;
	private List<Object> list;
	private int pageSize;
	private int page;
	private double recharge_amount;
	private Address address;
	private List<User> users = new ArrayList<User>();
	private List<Goods> goods = new ArrayList<Goods>();
	private List<Bill> bills;

	@Action(value = "login", results = { @Result(name = "success", type = "json") })
	public String login() {
		loginedUser = userService.login(user);
		if (loginedUser != null) {
			session.put("userid", user.getUserid());
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "validateNickname", results = { @Result(name = "success", type = "json") })
	public String validateNickname() {
		loginedUser = userService.validateNickname(user);
		if (loginedUser == null) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "findUserByUserid", results = { @Result(name = "success", type = "json") })
	public String findUserByUserid() {
		loginedUser = userService.findUserByUserid(userid);
		if (loginedUser == null) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "register", results = { @Result(name = "success", type = "json") })
	public String register() {
		String userid = java.util.UUID.randomUUID().toString().replace("-", "");
		user.setUserid(userid);
		user.setBalance(0.0);
		user.setPhoto("http://img0.imgtn.bdimg.com/it/u=452791011,331966209&fm=27&gp=0.jpg");
		if (userService.register(user)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "modifyPasswd", results = { @Result(name = "success", type = "json") })
	public String modifyPass() {
		if (userService.modifyPasswd(user)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "modifyBalance", results = { @Result(name = "success", type = "json") })
	public String modifyBalance() {
		if (bill.getState() == 2) {
			bill.setPrice(-bill.getPrice());
		}
		user.setBalance(userService.findUserByUserid(user.getUserid())
				.getBalance() + bill.getPrice());
		if (userService.modifyBalance(user)) {
			if (bill.getState() == null) {
				bill.setState((long) 0);
			}
			bill.setUserid(user.getUserid());
			if (bill.getState() == 2) {
				bill.setPrice(-bill.getPrice());
			}
			bill.setCreatetime(new Timestamp(System.currentTimeMillis()));
			userService.addBill(bill);
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "modifyUserBaseInfo", results = { @Result(name = "success", type = "json") })
	public String modifyUserBaseInfo() {
		User u=userService.findUserByUserid(user.getUserid());
		u.setNickname(user.getNickname());
		u.setPasswd(user.getPasswd());
		u.setSchool(user.getSchool());
		if (userService.modifyUserBaseInfo(u)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "addAddress", results = { @Result(name = "success", type = "json") })
	public String addAddress() {
		user = userService.findUserByUserid(user.getUserid());
		address.setUserid(user.getUserid());
		user.setAddress(address);
		if (userService.updateAddress(user)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "updateAddress", results = { @Result(name = "success", type = "json") })
	public String updateAddress() {
		user = userService.findUserByUserid(user.getUserid());
		address.setUserid(user.getUserid());
		address.setId(user.getAddress().getId());
		user.setAddress(address);
		if (userService.updateAddress(user)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "deleteAddress", results = { @Result(name = "success", type = "json") })
	public String deleteAddress() {
		user = userService.findUserByUserid(user.getUserid());
		userService.removeAddress(user.getAddress());
		user.setAddress(address);
		if (userService.updateAddress(user)) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "findUserAndGoods", results = { @Result(name = "success", type = "json") })
	public String findUserAndGoods() {
		list = userService.findUserAndGoods(pageSize, page);
		if (list.size() != 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				users.add((User) obj[0]);
				goods.add((Goods) obj[1]);
			}
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	@Action(value = "findBillByUseridForPage", results = { @Result(name = "success", type = "json") })
	public String findBillByUseridForPage() {
		bills = userService.findBillByUseridForPage(user.getUserid(), pageSize,
				page);
		if (bills != null) {
			code = "1";
		} else {
			code = "0";
		}
		return SUCCESS;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public User getLoginedUser() {
		return loginedUser;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public double getRecharge_amount() {
		return recharge_amount;
	}

	public void setRecharge_amount(double recharge_amount) {
		this.recharge_amount = recharge_amount;
	}

	public User getUser() {
		return user;
	}

	public void setLoginedUser(User loginedUser) {
		this.loginedUser = loginedUser;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
