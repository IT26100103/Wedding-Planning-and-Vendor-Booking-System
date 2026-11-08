package com.wedding.dao;

import com.wedding.model.CateringVendor;
import com.wedding.model.PhotographyVendor;
import com.wedding.model.Vendor;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class VendorDao {

    private static final String FILE_NAME = "vendors.txt";

    // READ ALL
    public List<Vendor> findAll() {
        List<Vendor> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Vendor v = parse(line);
            if (v != null) list.add(v);
        }
        return list;
    }

    private Vendor parse(String line) {
        String[] p = line.split("[|]");
        if (p.length < 5) return null;                 // damaged line
        try {
            double price = Double.parseDouble(p[3]);
            if (p[4].equals("CATERING")) return new CateringVendor(p[0], p[1], p[2], price);
            if (p[4].equals("PHOTOGRAPHY")) return new PhotographyVendor(p[0], p[1], p[2], price);
            return null;                               // unknown category
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void saveAll(List<Vendor> vendors) {
        List<String> lines = new ArrayList<>();
        for (Vendor v : vendors) lines.add(v.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // CREATE
    public boolean add(Vendor v) {
        List<Vendor> vendors = findAll();
        for (Vendor x : vendors) {
            if (x.getId().equals(v.getId())) return false;   // id already used
        }
        vendors.add(v);
        saveAll(vendors);
        return true;
    }

    // READ (search by name or category)
    public List<Vendor> search(String keyword) {
        List<Vendor> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Vendor v : findAll()) {
            if (v.getName().toLowerCase().contains(k) || v.getCategory().toLowerCase().contains(k)) {
                result.add(v);
            }
        }
        return result;
    }

    public Vendor findById(String id) {
        for (Vendor v : findAll()) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    // Next free ID
    public String nextId() {
        int max = 0;
        for (Vendor v : findAll()) {
            if (v.getId() != null && v.getId().startsWith("V")) {
                try {
                    int num = Integer.parseInt(v.getId().substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("V%03d", max + 1);
    }

    // UPDATE
    public boolean update(Vendor updated) {
        List<Vendor> vendors = findAll();
        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getId().equals(updated.getId())) {
                vendors.set(i, updated);
                saveAll(vendors);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        List<Vendor> vendors = findAll();
        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getId().equals(id)) {
                vendors.remove(i);
                saveAll(vendors);
                return true;
            }
        }
        return false;
    }
}
