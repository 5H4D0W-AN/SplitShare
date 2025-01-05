package com.amitnehra.mappers;

import com.amitnehra.models.FriendRequests;
import com.amitnehra.models.enums.RequestStatus;
import org.hibernate.dialect.FirebirdDialect;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestMapper {
    public static List<FriendRequests> mapTuple(List<Tuple> list){
        return list.stream()
                .map(tuple -> {
                    FriendRequests fr = new FriendRequests();
                    fr.setRequestId(tuple.get("requestId", Long.class));
                    fr.setFromAccount(tuple.get("fromAccount", String.class));
                    fr.setToAccount(tuple.get("toAccount", String.class));
                    fr.setTimeStamp(tuple.get("timeStamp", LocalDate.class));
                    fr.setStatus(tuple.get("status", RequestStatus.class));
                    return fr;
                }).collect(Collectors.toList());
    }

}
