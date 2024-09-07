package wanted.goldroom.authentication.interfaces.user;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import wanted.goldroom.authentication.domain.user.UserCommand;
import wanted.goldroom.authentication.domain.user.UserInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserDtoMapper {
    UserCommand of(UserDto.SignupRequest request);

    UserDto.SignupResponse of(UserInfo userInfo);
}
