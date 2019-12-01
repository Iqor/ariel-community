package com.igor.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "model")
@Getter @Setter
public class Model implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "model_date", nullable = false)
    private Date modelDate;

    @Column(name = "file_path", nullable = false, unique = true)
    private String filePath;

    @Column(name = "file_hash", nullable = false, length = 30)
    private String fileHash;

    @Transient
    private MultipartFile file;

}


