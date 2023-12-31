package com.example.releases.domain;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.OffsetDateTime;
@JacksonXmlRootElement
public record Release (String version, OffsetDateTime createdAt, Boolean preRelease) {}