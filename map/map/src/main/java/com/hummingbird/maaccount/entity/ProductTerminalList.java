package com.hummingbird.maaccount.entity;

/**
 * 产品与可消费终端清单表
 */
public class ProductTerminalList {
    /**
     * 自增序号
     */
    private Integer idt_product_terminal_list;

    /**
     * 产品编码
     */
    private String productId;

    /**
     * 终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    private String terminalId;

    /**
     * @return 自增序号
     */
    public Integer getIdt_product_terminal_list() {
        return idt_product_terminal_list;
    }

    /**
     * @param idtProductTerminalList 
	 *            自增序号
     */
    public void setIdt_product_terminal_list(Integer idt_product_terminal_list) {
        this.idt_product_terminal_list = idt_product_terminal_list;
    }

    /**
     * @return 产品编码
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productid 
	 *            产品编码
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return 终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalid 
	 *            终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId == null ? null : terminalId.trim();
    }
}