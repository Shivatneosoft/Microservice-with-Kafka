package com.contact.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum ContactStatus {

    Active("Active"),

    Inactive("Inactive"),

    Deleted("Deleted");

    @Getter
    private String value;

    public static List<String> getValue(){
        return List.of(ContactStatus.values()).stream().map(data-> data.value).collect(Collectors.toList());
    }

}
