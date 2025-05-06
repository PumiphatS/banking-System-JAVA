public static void saveCurrentUser(String filename) {
    try {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder updatedContent = new StringBuilder();

        String header = reader.readLine(); // read and keep header
        updatedContent.append(header).append("\n");

        String line;
        while ((line = reader.readLine()) != null) {
            String[] column = line.split(",", -1);
            if (column.length >= 7 && column[0].equals(currentUser.getUsername())) {
                // Rewrite only this user's line
                String updatedLine = String.format("%s,%s,%s,%s,%s,%.2f,%.2f",
                        currentUser.getUsername(),
                        currentUser.getPassword(),
                        currentUser.getField3(),
                        currentUser.getField4(),
                        currentUser.getField5(),
                        currentUser.getCheckingBalance(),
                        currentUser.getSavingsBalance()
                );
                updatedContent.append(updatedLine).append("\n");
            } else {
                updatedContent.append(line).append("\n"); // keep line unchanged
            }
        }
        reader.close();

        // Write updated content back
        FileWriter writer = new FileWriter(file);
        writer.write(updatedContent.toString());
        writer.close();

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Failed to update user.csv", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
