package tdd.groomingzone.barbershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.barbershop.dto.BarberShopApiDto;
import tdd.groomingzone.barbershop.dto.SingleBarberShopResponse;
import tdd.groomingzone.barbershop.dto.PostBarberShopCommand;
import tdd.groomingzone.barbershop.service.PostBarberUseCase;

@RestController
@RequestMapping("/barber-shop")
public class PostBarberShopController {

    private final PostBarberUseCase postBarberUseCase;

    public PostBarberShopController(PostBarberUseCase postBarberUseCase) {
        this.postBarberUseCase = postBarberUseCase;
    }

    @PostMapping
    public ResponseEntity<BarberShopApiDto.Response> postBarberShop(@AuthenticationPrincipal UserDetails writer,
                                                                    @RequestBody BarberShopApiDto.Post dto) {
        PostBarberShopCommand command = PostBarberShopCommand.of(writer.getUsername(), dto);
        SingleBarberShopResponse commandResponse = postBarberUseCase.postBarberShop(command);
        BarberShopApiDto.Response responseDto = BarberShopApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}