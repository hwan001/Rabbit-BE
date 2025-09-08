package team.avgmax.rabbit.funding.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.funding.repository.FundingRepository;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
}
