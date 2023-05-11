import java.util.Arrays;

public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null && resume.uuid != null && resume.uuid.equals(uuid))
                return resume;
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid != null && storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                for (int j = i; j < storage.length - 1; j++) {
                    storage[j] = storage[j+1];
                    storage[j+1] = null;
                }
                break;
            }
        }
    }

    Resume[] getAll() {
        return Arrays.copyOf(storage, this.size());
    }

    int size() {
        int nonNullsCounter = 0;
        for (Resume resume : storage) {
            if (resume != null)
                nonNullsCounter++;
        }
        return nonNullsCounter;
    }
}
