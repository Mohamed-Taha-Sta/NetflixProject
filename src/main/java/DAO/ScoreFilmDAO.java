package DAO;

import Entities.Episode;
import Entities.Film;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreFilmDAO {
    private static final Connection conn = ConxDB.getInstance();

    //checks if the user have rated the film
    public static boolean Score_Exist(Film film, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "Select * from score_film where id_film=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
            pstmt.setLong(2, user.getID());
            rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //returns the user score
    public static double RetrieveUserScore(Film film, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "Select * from score_film where id_film=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
            pstmt.setLong(2, user.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(3);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    //adds the score by the user
    public static boolean Add_Score(Film film, User user, double score) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        try {
            sql = "INSERT INTO score_film (id_user,id_film,score) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, film.getId());
            pstmt.setDouble(3, score);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //deletes the score by the user
    public static boolean Delete_Score(Film film, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "DELETE FROM score_film WHERE id_user = ? AND id_film = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, film.getId());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //update the score by the user
    public static boolean Update_Score(Film film, User user, double score) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "UPDATE score_film SET score = ? WHERE id_user = ? AND id_film = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, score);
            pstmt.setLong(2, user.getID());
            pstmt.setLong(3, film.getId());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //return average film score by all users
    public static double GetFilmScore(Film film) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        try {
            sql = "SELECT AVG(score) FROM score_film WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    public static int GetNumberVotesFilm(Film film) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        int count = 0;

        try {
            sql = "SELECT COUNT(*) FROM score_film WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return count;
    }


}