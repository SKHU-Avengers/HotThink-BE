package skhu.ht.hotthink.api.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.payment.repository.PayListRepository;
import skhu.ht.hotthink.api.payment.repository.ReputationRepository;
import skhu.ht.hotthink.api.payment.repository.SubscribeRepository;

@Service
public class PayService {
    @Autowired
    ReputationRepository reputationRepository;
    @Autowired
    PayListRepository payListRepository;
    @Autowired
    SubscribeRepository subscribeRepository;



}
