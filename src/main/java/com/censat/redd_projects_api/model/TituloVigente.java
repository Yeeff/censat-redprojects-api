package com.censat.redd_projects_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "titulo_vigente")
public class TituloVigente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fid")
    private Integer fid;

    @Column(name = "codigo_exp", nullable = false)
    private String codigoExp;

    @Column(name = "titulo_est")
    private String tituloEst;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "municipios")
    private String municipios;

    @Column(name = "departamen")
    private String departamen;

    @Column(name = "area_ha")
    private Double areaHa;

    @Column(name = "clasificac")
    private String clasificac;

    @Column(name = "etapa")
    private String etapa;

    @Column(name = "minerales")
    private String minerales;

    @Column(columnDefinition = "TEXT")
    private String geometry;

    // Constructors
    public TituloVigente() {}

    public TituloVigente(Integer fid, String codigoExp, String tituloEst, String modalidad,
                         String municipios, String departamen, Double areaHa, String clasificac,
                         String etapa, String minerales, String geometry) {
        this.fid = fid;
        this.codigoExp = codigoExp;
        this.tituloEst = tituloEst;
        this.modalidad = modalidad;
        this.municipios = municipios;
        this.departamen = departamen;
        this.areaHa = areaHa;
        this.clasificac = clasificac;
        this.etapa = etapa;
        this.minerales = minerales;
        this.geometry = geometry;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getCodigoExp() {
        return codigoExp;
    }

    public void setCodigoExp(String codigoExp) {
        this.codigoExp = codigoExp;
    }

    public String getTituloEst() {
        return tituloEst;
    }

    public void setTituloEst(String tituloEst) {
        this.tituloEst = tituloEst;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getMunicipios() {
        return municipios;
    }

    public void setMunicipios(String municipios) {
        this.municipios = municipios;
    }

    public String getDepartamen() {
        return departamen;
    }

    public void setDepartamen(String departamen) {
        this.departamen = departamen;
    }

    public Double getAreaHa() {
        return areaHa;
    }

    public void setAreaHa(Double areaHa) {
        this.areaHa = areaHa;
    }

    public String getClasificac() {
        return clasificac;
    }

    public void setClasificac(String clasificac) {
        this.clasificac = clasificac;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getMinerales() {
        return minerales;
    }

    public void setMinerales(String minerales) {
        this.minerales = minerales;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
