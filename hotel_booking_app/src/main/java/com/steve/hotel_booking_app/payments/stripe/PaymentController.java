package com.steve.hotel_booking_app.payments.stripe;


import com.steve.hotel_booking_app.common.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/stripe")
@RequiredArgsConstructor
@Tag(name = "Payments/Stripe")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Response> createAPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.createPaymentIntent(paymentRequest);
        Response response = Response.builder()
                .transactionId(paymentResponse.getTransactionId())
                .build();
        return  ResponseEntity.ok(response);
    }

    @PostMapping("/payment/update")
    public ResponseEntity<Response> updatePaymentBooking(@RequestBody  PaymentRequest paymentRequest) {
       paymentService.updatePaymentBooking(paymentRequest);
        Response response = Response.builder()
                .status(200)
                .message("Payment was successfull")
                .build();
        return  ResponseEntity.ok(response);
    }

}
