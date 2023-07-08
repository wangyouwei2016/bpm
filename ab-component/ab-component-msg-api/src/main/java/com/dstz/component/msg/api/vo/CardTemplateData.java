package com.dstz.component.msg.api.vo;

import java.io.Serializable;

/**
 * <p>
 * 消息模板dto
 * </p>
 *
 * @author lightning
 * @since 2022-11-17
 */
public class CardTemplateData implements Serializable {
    private String cardTitle;
    private String cardUrl;
    private String cardContent;

    public CardTemplateData() {
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }
}
