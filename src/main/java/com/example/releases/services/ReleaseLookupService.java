package com.example.releases.services;

import com.example.releases.domain.Release;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReleaseLookupService {
    ReleasesApiService releasesApiService;
    public List<Release> findReleasesBy(String createdBefore, String prerelease) {
        var releases = releasesApiService.fetchReleases();
        if (releases != null)  {
           releases = releases.stream().filter(release ->
                            createdBefore != null && !createdBefore.isEmpty() && !createdBefore.isBlank()
                                    ? release.createdAt().isBefore(OffsetDateTime.parse(createdBefore)) : true)
                    .filter(release ->
                            prerelease != null && !prerelease.isEmpty() && !prerelease.isBlank()
                            ? Boolean.parseBoolean(prerelease) == release.preRelease() : true)

                    .collect(Collectors.toList());
        } else {
            releases = List.of();
        }
        return releases;
    }
}
