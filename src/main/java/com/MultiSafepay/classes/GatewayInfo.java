package com.MultiSafepay.classes;

public class GatewayInfo {

	public String issuer_id = "";
	public Integer qr_size = null;
	public Integer qr_enabled;
	public String birthday = "";
	public String gender = "";
	public String bank_account = "";
	public String phone = "";
	public String email = "";
	public String referrer = "";
	public String user_agent = "";
	public String account_id = "";
	public String account_holder_name = "";
	public String account_holder_city = "";
	public String account_holder_country = "";
	public String account_holder_iban = "";
	public String account_holder_bic = "";
	public String card_number = "";
	public String card_holder_name = "";
	public String card_expiry_date = "";
	public String card_cvc = "";
	public String emandate = "";
	public String company = "";
	public String po_number = "";
	public String coc = "";
	public String vat = "";
	public String collecting_flow = "";
	public String action_on_declined = "";
	public String company_type = "";
	public String term_url = "";

	public static GatewayInfo DirectBank(String account_holder_name,
			String account_holder_city, String account_holder_country,
			String account_holder_iban, String account_holder_bic) {
		GatewayInfo info = new GatewayInfo();
		info.account_holder_name = account_holder_name;
		info.account_holder_city = account_holder_city;
		info.account_holder_country = account_holder_country;
		info.account_holder_iban = account_holder_iban;
		info.account_holder_bic = account_holder_bic;
		return info;
	}

	public static GatewayInfo PayAfterDelivery(String birthday,
			String bank_account, String phone, String email, String referrer,
			String user_agent) {
		GatewayInfo info = new GatewayInfo();
		info.birthday = birthday;
		info.bank_account = bank_account;
		info.phone = phone;
		info.email = email;
		info.referrer = referrer;
		info.user_agent = user_agent;
		return info;
	}

	public static GatewayInfo Einvoice(String birthday, String bank_account,
			String phone, String email, String referrer, String user_agent) {
		GatewayInfo info = new GatewayInfo();
		info.birthday = birthday;
		info.bank_account = bank_account;
		info.phone = phone;
		info.email = email;
		info.referrer = referrer;
		info.user_agent = user_agent;
		return info;
	}

	public static GatewayInfo Klarna(String birthday, String gender,
			String phone, String email) {
		GatewayInfo info = new GatewayInfo();
		info.birthday = birthday;
		info.gender = gender;
		info.phone = phone;
		info.email = email;
		return info;
	}

	public static GatewayInfo Ideal(String issuer_id) {
		GatewayInfo info = new GatewayInfo();
		info.issuer_id = issuer_id;
		return info;
	}

	public static GatewayInfo IdealQR(Integer qr_size) {
		GatewayInfo info = new GatewayInfo();
		info.qr_size = qr_size;
		return info;
	}
}