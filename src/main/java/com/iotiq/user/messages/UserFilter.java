package com.iotiq.user.messages;

import com.iotiq.commons.message.request.PageableRequest;
import com.iotiq.commons.message.request.SearchRequest;
import com.iotiq.user.domain.AccountInfo_;
import com.iotiq.user.domain.Person_;
import com.iotiq.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.iotiq.commons.util.NullHandlerUtil.setIfNotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserFilter extends PageableRequest implements SearchRequest<User> {
    String name;

    @Override
    public Specification<User> buildSpecification() {
        return fieldNameIsLike(name, Person_.FIRST_NAME)
                .or(fieldNameIsLike(name, Person_.LAST_NAME))
                .or(fieldNameIsLike(name, AccountInfo_.USERNAME));
    }

    private Specification<User> fieldNameIsLike(String name, String fieldName) {
        return (root, query, cb) ->
                setIfNotNull(getName(), () -> cb.like(cb.lower(root.get(fieldName)), "%" + name.toLowerCase() + "%"));
    }


}
